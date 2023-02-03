package com.along1358.AuglyDemo.constants;

import com.along1358.AuglyDemo.BuildConfig;

import java.io.File;

public class ServiceConstant {
    public static final String BASE_URL = "http://192.168.1.230:8080/";
    public static final String RES_URL_UPDATE_INFO_JSON = "update/update.json";
    public static final String RES_URL_PATCH_INFO_JSON = "patch/" + BuildConfig.VERSION_NAME + "/patch.json";
}
