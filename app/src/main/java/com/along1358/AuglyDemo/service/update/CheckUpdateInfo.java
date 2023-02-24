package com.along1358.AuglyDemo.service.update;

import com.along1358.AuglyDemo.constants.ServiceConstant;
import com.along1358.AuglyDemo.retrofit.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class CheckUpdateInfo {
    private static class Holder {
        private static CheckUpdateInfo instance = new CheckUpdateInfo();
    }

    public static CheckUpdateInfo getInstance() {
        return CheckUpdateInfo.Holder.instance;
    }

    private interface UpdateInfoRequest {
        @POST("update/checkVersion")
        Call<UpdateInfoResponseBody> getUpdateInfo(@Query("versionCode") int versionCode, @Query("appKey") String appKey);
    }

    public void exec(int curVerCode, String appKey, Callback<UpdateInfoResponseBody> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ServiceConstant.BASE_URL_XUpdateService)
                .build();
        CheckUpdateInfo.UpdateInfoRequest request = retrofit.create(CheckUpdateInfo.UpdateInfoRequest.class);
        request.getUpdateInfo(curVerCode, appKey).enqueue(callback);
    }
}
