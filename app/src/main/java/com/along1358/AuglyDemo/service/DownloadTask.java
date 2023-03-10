package com.along1358.AuglyDemo.service;

import com.along1358.AuglyDemo.retrofit.adapter.rxjava3.RxJava3CallAdapterFactory;
import com.along1358.AuglyDemo.retrofit.converter.gson.GsonConverterFactory;
import com.along1358.AuglyDemo.service.patch.DownloadResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public class DownloadTask {
    private DownloadRequest request;

    private DownloadInfo downloadInfo;
    protected int progress = -1;
    protected Disposable subscribe;

    public DownloadTask() {
        downloadInfo = new DownloadInfo();
    }

    private interface DownloadRequest {
        @Streaming
        @GET
        Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);
    }

    public void download(String baseUrl, String url, String path, DownloadListener listener) {
        downloadInfo.url = url;
        downloadInfo.savePath = path;

        final DownloadInterceptor interceptor = new DownloadInterceptor(new DownloadProgressListener() {
            @Override
            public void progress(long read, long contentLength, boolean done) {
                if (downloadInfo.contentLength > contentLength) {
                    read = read + (downloadInfo.contentLength - contentLength);
                } else {
                    downloadInfo.contentLength = contentLength;
                }
                downloadInfo.readLength = read;

                Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Integer value) {
                        if (listener != null) {
                            if (!done) {
                                int progressNew = (int) (100 * downloadInfo.readLength / downloadInfo.contentLength);
                                if (progressNew > progress) {
                                    progress = progressNew;
                                    listener.onProgress(progress);
                                }
                            } else {
                                progress = -1;
                                listener.onFinish(downloadInfo.savePath);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null)
                            listener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        if (request == null) {
            request = retrofit.create(DownloadRequest.class);
            downloadInfo.setRequest(request);
        } else {
            request = downloadInfo.getRequest();
        }

        subscribe = request.download("bytes=" + downloadInfo.readLength + "-", downloadInfo.url)
                /*????????????*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RetryWhenNetworkException())
                /* ?????????????????????????????????ResponseBody??????DownInfo */
                .map(new Function<ResponseBody, DownloadInfo>() {
                    @Override
                    public DownloadInfo apply(ResponseBody responseBody) throws Exception {
                        try {
                            //????????????
                            writeCache(responseBody, new File(downloadInfo.savePath), downloadInfo);
                        } catch (IOException e) {
                        }
                        return downloadInfo;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadInfo>() {
                    @Override
                    public void accept(DownloadInfo downloadInfo) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(listener!=null)
                            listener.onError(throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                });
        if (listener != null)
            listener.onStarted();
    }

    private void writeCache(ResponseBody responseBody, File file, DownloadInfo info) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        long allLength;
        if (info.contentLength == 0) {
            allLength = responseBody.contentLength();
        } else {
            allLength = info.contentLength;
        }

        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                info.readLength, allLength - info.readLength);
        byte[] buffer = new byte[1024 * 10];
        int len;
        int record = 0;
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        responseBody.byteStream().close();
        if (channelOut != null) {
            channelOut.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }

    protected class RetryWhenNetworkException implements Function<Observable<? extends Throwable>, Observable<?>> {
        //    retry??????
        private int count = 3;
        //    ??????
        private long delay = 3000;
        //    ????????????
        private long increaseDelay = 3000;

        public RetryWhenNetworkException() {

        }

        public RetryWhenNetworkException(int count, long delay) {
            this.count = count;
            this.delay = delay;
        }

        public RetryWhenNetworkException(int count, long delay, long increaseDelay) {
            this.count = count;
            this.delay = delay;
            this.increaseDelay = increaseDelay;
        }

        @Override
        public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
            return observable.zipWith(Observable.range(1, count + 1), new BiFunction<Throwable, Integer, Wrapper>() {

                @Override
                public Wrapper apply(Throwable throwable, Integer integer) throws Exception {
                    //???????????? ???????????????????????????Observable<Wrapper>
                    return new Wrapper(throwable, integer);
                }
            }).flatMap(new Function<Wrapper, ObservableSource<?>>() {
                @Override
                public ObservableSource<?> apply(Wrapper wrapper) throws Exception {
                    //????????????
                    if ((wrapper.throwable instanceof ConnectException
                            || wrapper.throwable instanceof SocketTimeoutException
                            || wrapper.throwable instanceof TimeoutException)
                            && wrapper.index < count + 1) { //??????????????????????????????????????????????????????????????????onCompleted
                        return Observable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);

                    }
                    return Observable.error(wrapper.throwable);
                }
            });
        }

        private class Wrapper {
            private int index;
            private Throwable throwable;

            public Wrapper(Throwable throwable, int index) {
                this.index = index;
                this.throwable = throwable;
            }
        }
    }

    private class DownloadInfo {
        public String savePath;
        public long contentLength;
        public long readLength;
        public String url;
        private DownloadRequest request;

        public DownloadRequest getRequest() {
            return request;
        }

        public void setRequest(DownloadRequest request) {
            this.request = request;
        }
    }

    protected class DownloadInterceptor implements Interceptor {

        private DownloadProgressListener listener;

        public DownloadInterceptor(DownloadProgressListener listener) {
            this.listener = listener;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            return originalResponse.newBuilder()
                    .body(new DownloadResponseBody(originalResponse.body(), listener))
                    .build();
        }
    }

}
