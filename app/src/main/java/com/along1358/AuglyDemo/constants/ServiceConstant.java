package com.along1358.AuglyDemo.constants;

import com.along1358.AuglyDemo.BuildConfig;
import com.along1358.AuglyDemo.R;
import com.along1358.AuglyDemo.utils.AppUtils;
import com.along1358.AuglyDemo.utils.ContextUtils;

public class ServiceConstant {
    //retrofit base url
    public static final String BASE_URL_LOCAL_TEST = "http://127.0.0.1/";

    //tinker json url
    public static final String RES_URL_PATCH_INFO_JSON = "patch/" + "AuglyDemo" + "/" + BuildConfig.VERSION_NAME + "/patch.json";

    //xUpdateService config
    //xUpdateService 版本 v1.0.0
    public static final String APP_KEY_XUpdateService = AppConstant.APP_NAME;
    public static final String BASE_URL_XUpdateService_ENV_TEST = "http://192.168.1.230:1111/";
    public static final String BASE_URL_XUpdateService_ENV_PRODUSE = "http://192.168.1.230:1111/";

    //XUpdateService api
    public static final String API_URL_PREFIX_DOWNLOAD_APP = "/update/apk/";
}
