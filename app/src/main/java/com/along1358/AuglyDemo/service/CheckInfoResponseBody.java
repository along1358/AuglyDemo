package com.along1358.AuglyDemo.service;

public class CheckInfoResponseBody {
    private int versionCode;
    private String versionName;
    private String updateContent;
    private String apkUrl;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    @Override
    public String toString() {
        return "UpdateInfoResponseBody{" +
                "versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", apkUrl='" + apkUrl + '\'' +
                '}';
    }
}
