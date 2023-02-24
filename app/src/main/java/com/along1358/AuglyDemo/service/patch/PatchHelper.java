package com.along1358.AuglyDemo.service.patch;

import com.along1358.AuglyDemo.constants.AppConstant;
import com.along1358.AuglyDemo.service.DownloadListener;
import com.along1358.AuglyDemo.tinker.TinkerHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatchHelper {
    public static PatchHelper getInstance() {
        return PatchHelper.Holder.instance;
    }

    private static class Holder {
        private static PatchHelper instance = new PatchHelper();
    }

    public void patch() {
        if (!AppConstant.ENABLE_HOTFIX) return;
        //已加载过补丁，查询是否有新补丁
        if (TinkerHelper.getInstance().isTinkerLoaded()) {
            PatchInfoService.getInstance().exec(new Callback<CheckInfoResponseBody>() {
                @Override
                public void onResponse(Call<CheckInfoResponseBody> call, Response<CheckInfoResponseBody> response) {
                    CheckInfoResponseBody responseBody = response.body();
                    String patchVersion = TinkerHelper.getInstance().getPatchVersion();
                    String tinkerId = TinkerHelper.getInstance().getTinkerId();
                    if (!patchVersion.equals(responseBody.getVersionName())
                            && tinkerId.equals(AppConstant.TINKER_ID)) {
                        downloadPatch(responseBody);
                    }
                }

                @Override
                public void onFailure(Call<CheckInfoResponseBody> call, Throwable t) {

                }
            });
        } else { //未加载过补丁或加载失败，重新下载并加载
            PatchInfoService.getInstance().exec(new Callback<CheckInfoResponseBody>() {
                @Override
                public void onResponse(Call<CheckInfoResponseBody> call, Response<CheckInfoResponseBody> response) {
                    CheckInfoResponseBody responseBody = response.body();
                    downloadPatch(responseBody);
                }

                @Override
                public void onFailure(Call<CheckInfoResponseBody> call, Throwable t) {

                }
            });
        }
    }

    private void downloadPatch(CheckInfoResponseBody info) {
        DownloadService.getInstance().download(info, AppConstant.PATCH_PKG_PATH, new DownloadListener() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onFinish(String path) {
                TinkerHelper.getInstance().loadPatch(path);
            }

            @Override
            public void onError(String msg) {
            }
        });
    }
}
