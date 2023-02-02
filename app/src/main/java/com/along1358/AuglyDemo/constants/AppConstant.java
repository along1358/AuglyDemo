package com.along1358.AuglyDemo.constants;

import android.os.Environment;

import java.io.File;

public class AppConstant {
    //启停版本更新
    public static final boolean ENABLE_UPDATE = true;

    //启停热修复,启用热修复需先启用版本更新
    public static final boolean ENABLE_PATCH = false;

    public static final String SP_FILE_NAME = "sp";
    public static final String SP_KEY_PATCH_INFO_VERSION_CODE = "patchInfoVersionCode";

    public static final String EX_STORAGE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PATCH_PKG_DIR = new File(EX_STORAGE_ROOT, "patch").getAbsolutePath();
    public static final String PATCH_PKG_NAME = "patch.apk";
    public static final String PATCH_PKG_PATH = new File(PATCH_PKG_DIR, PATCH_PKG_NAME).getPath();
}
