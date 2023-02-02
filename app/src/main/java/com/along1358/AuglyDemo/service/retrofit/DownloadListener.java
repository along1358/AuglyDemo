package com.along1358.AuglyDemo.service.retrofit;

public interface DownloadListener {
    void onStarted(CheckInfoResponseBody info, String path);

    void onProgress(CheckInfoResponseBody info, int progress);

    void onFinish(CheckInfoResponseBody info, String path);

    void onError(CheckInfoResponseBody info, String msg);
}
