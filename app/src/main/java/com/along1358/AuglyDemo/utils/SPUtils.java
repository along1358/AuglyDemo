package com.along1358.AuglyDemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.along1358.AuglyDemo.constants.AppConstant;

public class SPUtils {
    public static <T> void set(String key, T value) {
        SharedPreferences sp = AppUtils.getApp().getSharedPreferences(AppConstant.SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof Integer)
            edit.putInt(key, (Integer) value);
        else if (value instanceof String)
            edit.putString(key, (String) value);
        else if (value instanceof Boolean)
            edit.putBoolean(key, (Boolean) value);
        edit.commit();
    }

    public static <T> T get(String key, T defValue) {
        SharedPreferences sp = AppUtils.getApp().getSharedPreferences(AppConstant.SP_FILE_NAME, Context.MODE_PRIVATE);
        Object ret = null;
        if (defValue instanceof Integer)
            ret = sp.getInt(key, (Integer) defValue);
        else if (defValue instanceof String)
            ret = sp.getString(key, (String) defValue);
        else if (defValue instanceof Boolean)
            ret = sp.getBoolean(key, (Boolean) defValue);
        return (T) ret;
    }

    public static void delete(String key) {
        SharedPreferences sp = AppUtils.getApp().getSharedPreferences(AppConstant.SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }
}
