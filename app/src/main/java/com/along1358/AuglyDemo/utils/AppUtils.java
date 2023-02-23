package com.along1358.AuglyDemo.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;

public class AppUtils {
    private static long quitCnt = 0;

    public static void askQuit() {
        if (System.currentTimeMillis() - quitCnt > 2000) {
            Toast.makeText(ContextUtils.getApp(), "再按一次退出", Toast.LENGTH_SHORT).show();
            quitCnt = System.currentTimeMillis();
        } else {
            ContextUtils.exitApp();
        }
    }

    public static void installApp(String apkPath) {
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(ContextUtils.getApp(), ContextUtils.getApp().getPackageName() + ".fileProvider", new File(apkPath));
        } else {
            uri = Uri.fromFile(new File(apkPath));
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        ContextUtils.getApp().startActivity(intent);
    }

}
