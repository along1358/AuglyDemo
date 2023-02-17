package com.along1358.AuglyDemo.constants;


import com.along1358.AuglyDemo.BuildConfig;
import com.along1358.AuglyDemo.R;
import com.along1358.AuglyDemo.utils.AppUtils;

public class ServiceConstant {
    public static final String BASE_URL = "http://192.168.1.230:8080/";
    public static final String APP_NAME = AppUtils.getApp().getString(R.string.app_name);
    public static final String RES_URL_UPDATE_INFO_JSON = "update/" + "AuglyDemo" + "/update.json";
    public static final String RES_URL_PATCH_INFO_JSON = "patch/" + "AuglyDemo" + "/" + BuildConfig.VERSION_NAME + "/patch.json";
}
