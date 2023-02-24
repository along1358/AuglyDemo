package com.along1358.AuglyDemo.service.update;

public class UpdateInfoResponseBody {
    private int code;
    private String msg;
    private Data data;

    public class Data {
        int versionId;
        int updateStatus;
        int versionCode;
        String versionName;
        String uploadTime;
        int apkSize;
        String appKey;
        String modifyContent;
        String downloadUrl;
        String apkMd5;

        public int getVersionId() {
            return versionId;
        }

        public void setVersionId(int versionId) {
            this.versionId = versionId;
        }

        public int getUpdateStatus() {
            return updateStatus;
        }

        public void setUpdateStatus(int updateStatus) {
            this.updateStatus = updateStatus;
        }

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

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public int getApkSize() {
            return apkSize;
        }

        public void setApkSize(int apkSize) {
            this.apkSize = apkSize;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getModifyContent() {
            return modifyContent;
        }

        public void setModifyContent(String modifyContent) {
            this.modifyContent = modifyContent;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getApkMd5() {
            return apkMd5;
        }

        public void setApkMd5(String apkMd5) {
            this.apkMd5 = apkMd5;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "versionId=" + versionId +
                    ", updateStatus=" + updateStatus +
                    ", versionCode=" + versionCode +
                    ", versionName='" + versionName + '\'' +
                    ", uploadTime='" + uploadTime + '\'' +
                    ", apkSize=" + apkSize +
                    ", appKey='" + appKey + '\'' +
                    ", modifyContent='" + modifyContent + '\'' +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    ", apkMd5='" + apkMd5 + '\'' +
                    '}';
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UpdateInfoResponseBody{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
