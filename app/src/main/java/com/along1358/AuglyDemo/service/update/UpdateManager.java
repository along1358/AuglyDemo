package com.along1358.AuglyDemo.service.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.along1358.AuglyDemo.R;
import com.along1358.AuglyDemo.constants.AppConstant;
import com.along1358.AuglyDemo.service.DownloadListener;
import com.along1358.AuglyDemo.service.DownloadTask;
import com.along1358.AuglyDemo.utils.AppUtils;

import java.io.File;

public class UpdateManager {
    protected ProgressDialog progressDialog;

    public void update(Context context, String url, String newVersion) {
        String apkPath = new File(AppConstant.UPDATE_PKG_DIR, newVersion + ".apk").getPath();
        if (new File(apkPath).exists()) AppUtils.installApp(apkPath);
        else {
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.download(url,
                    apkPath,
                    new DownloadListener() {
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
                    }
            );
        }
    }
}
