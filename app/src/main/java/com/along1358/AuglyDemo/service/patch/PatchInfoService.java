package com.along1358.AuglyDemo.service.patch;

import com.along1358.AuglyDemo.constants.ServiceConstant;
import com.along1358.AuglyDemo.retrofit.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class PatchInfoService {
    private static class Holder {
        private static PatchInfoService instance = new PatchInfoService();
    }

    public static PatchInfoService getInstance() {
        return PatchInfoService.Holder.instance;
    }

    private interface PatchInfoRequest {
        @GET(ServiceConstant.RES_URL_PATCH_INFO_JSON)
        Call<CheckInfoResponseBody> getPatchInfo();
    }

    public void exec(Callback<CheckInfoResponseBody> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ServiceConstant.BASE_URL)
                .build();
        PatchInfoService.PatchInfoRequest request = retrofit.create(PatchInfoService.PatchInfoRequest.class);
        request.getPatchInfo().enqueue(callback);
    }
}
