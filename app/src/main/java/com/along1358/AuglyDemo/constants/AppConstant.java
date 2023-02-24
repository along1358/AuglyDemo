package com.along1358.AuglyDemo.constants;

import android.os.Environment;

import com.along1358.AuglyDemo.BuildConfig;
import com.along1358.AuglyDemo.R;
import com.along1358.AuglyDemo.utils.ContextUtils;

import java.io.File;

public class AppConstant {
    public static final String APP_NAME = ContextUtils.getApp().getResources().getString(R.string.app_name);

    //接入蒲公英SDK,否则接入XUpdateService后台
    public static final boolean USE_PGY_SDK = false;

    //sharedPreferences file name
    public static final String SP_FILE_NAME = "sp";

    //external storage root path
    public static final String EX_STORAGE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    //download update apk file dir
    public static final String UPDATE_PKG_DIR = new File(EX_STORAGE_ROOT, APP_NAME + "_update").getPath();

    //启停热修复
    public static final boolean ENABLE_HOTFIX = false;

    //tinker config
    public static final String PATCH_PKG_DIR = new File(EX_STORAGE_ROOT, "patch").getAbsolutePath();
    public static final String PATCH_PKG_NAME = "patch.apk";
    public static final String PATCH_PKG_PATH = new File(PATCH_PKG_DIR, PATCH_PKG_NAME).getPath();
    public static final String TINKER_ID = "tinker_id_" + BuildConfig.TINKER_ID;
}
