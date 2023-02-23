package com.along1358.AuglyDemo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.along1358.AuglyDemo.base.SimplePermissionActivity;
import com.along1358.AuglyDemo.constants.AppConstant;
import com.along1358.AuglyDemo.service.update.UpdateManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import java.util.List;

public class MainActivity extends SimplePermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, new PermissionCallback() {
            @Override
            public void onAllGranted() {
                updateApp();
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {

            }
        });
    }

    private void updateApp() {
        if (!BuildConfig.USE_NETWORK) return;
        if (AppConstant.USE_PGY_SDK) {
            //蒲公英平台获取版本更新
            updateAppWithPgySdk(this);
        }
    }

    private void updateAppWithPgySdk(Activity activity) {
        PgyUpdateManager.register(activity, new UpdateManagerListener() {
            @Override
            public void onNoUpdateAvailable() {

            }

            @Override
            public void onUpdateAvailable(String s) {
                final AppBean appBean = getAppBeanFromString(s);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.dialog_title)
                        .setMessage(appBean.getReleaseNote())
                        .setPositiveButton(R.string.txt_cancel, null)

                        .setNegativeButton(
                                R.string.txt_update,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        startDownloadTask(MainActivity.this, appBean.getDownloadURL());
                                        new UpdateManager().update(activity, appBean.getDownloadURL(), appBean.getVersionName());
                                    }
                                })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        })
                        .show();
            }
        });
    }
}