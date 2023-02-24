package com.along1358.AuglyDemo.constants;


import com.along1358.AuglyDemo.BuildConfig;
import com.along1358.AuglyDemo.R;
import com.along1358.AuglyDemo.utils.AppUtils;
import com.along1358.AuglyDemo.utils.ContextUtils;

public class ServiceConstant {
    public static final String BASE_URL = "http://192.168.1.230:8080/";
    public static final String APP_NAME = ContextUtils.getApp().getString(R.string.app_name);

    //json url
    public static final String RES_URL_UPDATE_INFO_JSON = "update/" + "AuglyDemo" + "/update.json";
    public static final String RES_URL_PATCH_INFO_JSON = "patch/" + "AuglyDemo" + "/" + BuildConfig.VERSION_NAME + "/patch.json";

    public static final String APP_KEY_XUpdateService = "along";//APP_NAME;
    public static final String BASE_URL_XUpdateService = "http://192.168.1.230:1111/";

    //XUpdateService api
    public static final String URL_PREFIX_DOWNLOAD_APP = "/update/apk/";
}
