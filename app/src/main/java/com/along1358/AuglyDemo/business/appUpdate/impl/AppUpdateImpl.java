package com.along1358.AuglyDemo.business.appUpdate.impl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.along1358.AuglyDemo.BuildConfig;
import com.along1358.AuglyDemo.R;
import com.along1358.AuglyDemo.business.appUpdate.AppUpdate;
import com.along1358.AuglyDemo.constants.AppConstant;
import com.along1358.AuglyDemo.constants.ServiceConstant;
import com.along1358.AuglyDemo.service.update.CheckUpdateInfo;
import com.along1358.AuglyDemo.service.update.UpdateInfoResponseBody;
import com.along1358.AuglyDemo.service.update.UpdateManager;
import com.along1358.AuglyDemo.utils.ContextUtils;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppUpdateImpl implements AppUpdate {

    @Override
    public void update() {
        Context topActivityOrApp = ContextUtils.getTopActivityOrApp();
        if (!(topActivityOrApp instanceof Activity)) return;

        //蒲公英平台获取版本更新
        if (AppConstant.USE_PGY_SDK) {
            PgyUpdateManager.register((Activity) topActivityOrApp, new UpdateManagerListener() {
                @Override
                public void onNoUpdateAvailable() {

                }

                @Override
                public void onUpdateAvailable(String s) {
                    final AppBean appBean = getAppBeanFromString(s);
                    new AlertDialog.Builder(topActivityOrApp)
                            .setTitle(R.string.dialog_title)
                            .setMessage(appBean.getReleaseNote())
                            .setPositiveButton(R.string.txt_cancel, null)

                            .setNegativeButton(
                                    R.string.txt_update,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                        startDownloadTask(MainActivity.this, appBean.getDownloadURL());
                                            new UpdateManager().update(topActivityOrApp,
                                                    appBean.getDownloadURL(),
                                                    appBean.getVersionName(),
                                                    true);
                                        }
                                    })
                            .show();
                }
            });
        }
        //XUpdateService后台获取版本更新
        else {
            int curVerCode = BuildConfig.VERSION_CODE;
            String appKey = ServiceConstant.APP_KEY_XUpdateService;
            CheckUpdateInfo.getInstance().exec(curVerCode, appKey, new Callback<UpdateInfoResponseBody>() {
                @Override
                public void onResponse(Call<UpdateInfoResponseBody> call, Response<UpdateInfoResponseBody> response) {
                    UpdateInfoResponseBody body = response.body();
                    if (body.getData().getVersionName() != null) {
                        new AlertDialog.Builder(topActivityOrApp)
                                .setTitle(R.string.dialog_title)
                                .setMessage(body.getData().getModifyContent())
                                .setPositiveButton(R.string.txt_cancel, null)

                                .setNegativeButton(
                                        R.string.txt_update,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                new UpdateManager().update(
                                                        topActivityOrApp,
                                                        body.getData().getDownloadUrl(),
                                                        body.getData().getVersionName(),
                                                        false);
                                            }
                                        })
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateInfoResponseBody> call, Throwable t) {

                }
            });
        }
    }
}
