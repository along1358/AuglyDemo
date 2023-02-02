package com.along1358.AuglyDemo.service.update;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.along1358.AuglyDemo.BuildConfig;
import com.along1358.AuglyDemo.MainActivity;
import com.along1358.AuglyDemo.constants.AppConstant;
import com.along1358.AuglyDemo.service.retrofit.CheckInfoResponseBody;
import com.along1358.AuglyDemo.tinkerUtils.PatchHelper;
import com.along1358.AuglyDemo.utils.AppUtils;
import com.hailong.appupdate.AppUpdateManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateHelper {
    public static UpdateHelper getInstance() {
        return UpdateHelper.Holder.instance;
    }

    private static class Holder {
        private static UpdateHelper instance = new UpdateHelper();
    }

    private String[] parseUpdateContent(String original, String separator) {
        return original.split(separator);
    }

    public void updateInfoCheck() {
        UpdateInfoService.getInstance().exec(new Callback<CheckInfoResponseBody>() {
            @Override
            public void onResponse(Call<CheckInfoResponseBody> call, Response<CheckInfoResponseBody> response) {
                CheckInfoResponseBody responseBody = response.body();
                int infoCode = responseBody.getVersionCode();
                String apkUrl = responseBody.getApkUrl();
                //发现新版本
                if (infoCode > BuildConfig.VERSION_CODE) {

                    Context topActivityOrApp = AppUtils.getTopActivityOrApp();
                    if (topActivityOrApp instanceof Activity) {
                        AppUpdateManager.Builder builder = new AppUpdateManager.Builder((Activity) topActivityOrApp);
                        builder.apkUrl(apkUrl)
                                .updateContent(parseUpdateContent(responseBody.getUpdateContent(), "\n"))
                                .updateForce(false)
                                .build();
                    }

                } else { //未有新版本，尝试打补丁
                    if (AppConstant.ENABLE_PATCH)
                        PatchHelper.getInstance().patch();
                }
            }

            //未上传新版本，尝试打补丁
            @Override
            public void onFailure(Call<CheckInfoResponseBody> call, Throwable t) {
                if (AppConstant.ENABLE_PATCH)
                    PatchHelper.getInstance().patch();
            }
        });
    }
}
