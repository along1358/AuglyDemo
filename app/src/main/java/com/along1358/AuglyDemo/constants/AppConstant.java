package com.along1358.AuglyDemo.constants;

import android.os.Environment;

import com.along1358.AuglyDemo.BuildConfig;

import java.io.File;

public class AppConstant {
    //启停热修复
    public static final boolean ENABLE_PATCH = true;

    //sharedPreferences
    public static final String SP_FILE_NAME = "sp";

    //tinker
    public static final String EX_STORAGE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PATCH_PKG_DIR = new File(EX_STORAGE_ROOT, "patch").getAbsolutePath();
    public static final String PATCH_PKG_NAME = "patch.apk";
    public static final String PATCH_PKG_PATH = new File(PATCH_PKG_DIR, PATCH_PKG_NAME).getPath();
    public static final String TINKER_ID = "tinker_id_" + BuildConfig.TINKER_ID;

}
