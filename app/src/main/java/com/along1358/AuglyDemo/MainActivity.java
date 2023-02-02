package com.along1358.AuglyDemo;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.along1358.AuglyDemo.base.SimplePermissionActivity;
import com.along1358.AuglyDemo.constants.AppConstant;
import com.along1358.AuglyDemo.service.patch.DownloadService;
import com.along1358.AuglyDemo.service.patch.PatchInfoService;
import com.along1358.AuglyDemo.service.retrofit.CheckInfoResponseBody;
import com.along1358.AuglyDemo.service.retrofit.DownloadListener;
import com.along1358.AuglyDemo.service.update.UpdateHelper;
import com.along1358.AuglyDemo.service.update.UpdateInfoService;
import com.along1358.AuglyDemo.tinkerUtils.PatchHelper;
import com.along1358.AuglyDemo.tinkerUtils.TinkerHelper;
import com.along1358.AuglyDemo.utils.AppSPUtils;
import com.hailong.appupdate.AppUpdateManager;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                if (AppConstant.ENABLE_UPDATE)
                    UpdateHelper.getInstance().updateInfoCheck();
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {

            }
        });
    }

}