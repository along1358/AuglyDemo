package com.along1358.AuglyDemo.service;

public interface DownloadListener {
    void onStarted();

    void onProgress(int progress);

    void onFinish(String path);

    void onError(String msg);
}
