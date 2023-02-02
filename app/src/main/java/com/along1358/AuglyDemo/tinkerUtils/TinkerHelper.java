package com.along1358.AuglyDemo.tinkerUtils;

import com.along1358.AuglyDemo.BuildConfig;
import com.along1358.AuglyDemo.utils.AppUtils;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class TinkerHelper {
    private Tinker tinker;

    public TinkerHelper() {
        tinker = Tinker.with(AppUtils.getApp());
    }

    public static TinkerHelper getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static TinkerHelper instance = new TinkerHelper();
    }

    public boolean isTinkerLoaded() {
        return tinker.isTinkerLoaded();
    }

    public void loadPatch(String path) {
        TinkerInstaller.onReceiveUpgradePatch(AppUtils.getApp(), path);
    }

    public String getTinkerId() {
        if (tinker.isTinkerLoaded())
            return tinker.getTinkerLoadResultIfPresent().getPackageConfigByName(ShareConstants.TINKER_ID);
        else return "";
    }

    public String getPatchVersion() {
        if (tinker.isTinkerLoaded())
            return tinker.getTinkerLoadResultIfPresent().getPackageConfigByName("patchVersion");
        else return "";
    }
}
