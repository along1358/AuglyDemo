package com.along1358.AuglyDemo.service.update;

import android.app.ProgressDialog;
import android.content.Context;

import com.along1358.AuglyDemo.BuildConfig;
import com.along1358.AuglyDemo.R;
import com.along1358.AuglyDemo.constants.AppConstant;
import com.along1358.AuglyDemo.constants.ServiceConstant;
import com.along1358.AuglyDemo.service.DownloadListener;
import com.along1358.AuglyDemo.service.DownloadTask;
import com.along1358.AuglyDemo.utils.AppUtils;

import java.io.File;

public class UpdateManager {
    protected ProgressDialog progressDialog;

    public void update(Context context, String url, String newVersion, boolean usePgy) {
        String apkPath = new File(AppConstant.UPDATE_PKG_DIR, newVersion + ".apk").getPath();
        if (new File(apkPath).exists())
            AppUtils.installApp(apkPath);
        else {
            DownloadListener downloadListener = new DownloadListener() {
                @Override
                public void onStarted() {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setProgressStyle(1);
                        progressDialog.setMessage(context.getString(R.string.dialog_msg_downloading));
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                }

                @Override
                public void onProgress(int progress) {
                    if (progressDialog != null)
                        progressDialog.setProgress(progress);
                }

                @Override
                public void onFinish(String path) {
                    if (progressDialog != null)
                        progressDialog.dismiss();

                    AppUtils.installApp(path);
                }

                @Override
                public void onError(String msg) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                }
            };

            if (usePgy) {
                new DownloadTask().download(ServiceConstant.BASE_URL_LOCAL_TEST, url, apkPath, downloadListener);
            } else {
                new DownloadTask().download(
                        BuildConfig.ENV_TEST ? ServiceConstant.BASE_URL_XUpdateService_ENV_TEST : ServiceConstant.BASE_URL_XUpdateService_ENV_PRODUSE,
                        ServiceConstant.API_URL_PREFIX_DOWNLOAD_APP + url,
                        apkPath,
                        downloadListener);
            }
        }
    }
}
