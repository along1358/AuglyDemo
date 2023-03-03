package com.along1358.AuglyDemo;

import android.Manifest;
import android.os.Bundle;

import com.along1358.AuglyDemo.base.SimplePermissionActivity;
import com.along1358.AuglyDemo.business.appUpdate.impl.AppUpdateImpl;

import java.util.List;

public class MainActivity extends SimplePermissionActivity {
    AppUpdateImpl appUpdateImpl;

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
                appUpdateImpl = new AppUpdateImpl();
                appUpdateImpl.update();
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {

            }
        });
    }
}