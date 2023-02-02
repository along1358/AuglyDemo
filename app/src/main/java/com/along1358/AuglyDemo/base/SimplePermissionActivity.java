package com.along1358.AuglyDemo.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class SimplePermissionActivity extends Activity {
    private final static int REQUEST_CODE = 0x01;
    private PermissionCallback callback;

    protected void requestPermissions(@NonNull String[] permissions, PermissionCallback callback) {
        if (permissions == null || permissions.length == 0)
            throw new NullPointerException("no request permissions?");

        setPermissionCallback(callback);

        List<String> permissionsRequest = new ArrayList();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : permissions) {
                if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                    permissionsRequest.add(p);
                }
            }
            if (permissionsRequest.isEmpty()) {
                if (callback != null)
                    callback.onAllGranted();
            } else {
                ActivityCompat.requestPermissions(this, permissionsRequest.toArray(new String[0]), REQUEST_CODE);
            }
        } else {
            if (callback != null)
                callback.onAllGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0)
            return;

        List<String> permissionsDenied = new ArrayList();
        if (requestCode == REQUEST_CODE) {
            for (int id = 0; id < grantResults.length; id++) {
                if (grantResults[id] == PackageManager.PERMISSION_DENIED) {
                    permissionsDenied.add(permissions[id]);
                }
            }
            if (!permissionsDenied.isEmpty()) {
                if (callback != null)
                    callback.onDenied(permissionsDenied);
            } else {
                if (callback != null)
                    callback.onAllGranted();
            }
        }
    }

    protected interface PermissionCallback {
        void onAllGranted();

        void onDenied(List<String> deniedPermissions);
    }

    private void setPermissionCallback(PermissionCallback callback) {
        this.callback = callback;
    }
}
