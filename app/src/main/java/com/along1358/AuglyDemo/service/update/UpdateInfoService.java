package com.along1358.AuglyDemo.service.update;

import com.along1358.AuglyDemo.constants.ServiceConstant;
import com.along1358.AuglyDemo.service.retrofit.CheckInfoResponseBody;
import com.along1358.AuglyDemo.service.retrofit.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class UpdateInfoService {
    private static class Holder {
        private static UpdateInfoService instance = new UpdateInfoService();
    }

    public static UpdateInfoService getInstance() {
        return Holder.instance;
    }

    private interface UpdateInfoRequest {
        @GET(ServiceConstant.RES_URL_UPDATE_INFO_JSON)
        Call<CheckInfoResponseBody> getUpdateInfo();
    }

    public void exec(Callback<CheckInfoResponseBody> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ServiceConstant.BASE_URL)
                .build();
        UpdateInfoRequest request = retrofit.create(UpdateInfoRequest.class);
        request.getUpdateInfo().enqueue(callback);
    }
}
