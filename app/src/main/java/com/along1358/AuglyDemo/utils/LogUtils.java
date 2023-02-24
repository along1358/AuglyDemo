package com.along1358.AuglyDemo.utils;

import android.util.Log;

import com.along1358.AuglyDemo.BuildConfig;

public class LogUtils {
    private static String mTag = "LogUtils";
    private static boolean mIsDebug = BuildConfig.DEBUG;

    public static void setTag(String tag) {
        mTag = tag;
    }

    public static void logI(String msg) {
        if (mIsDebug)
            Log.i(mTag, msg);
    }

    public static void logE(String msg) {
        Log.e(mTag, msg);
    }

}
