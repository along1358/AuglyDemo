package com.along1358.AuglyDemo.service;

public interface DownloadProgressListener {
    void progress(long read, long contentLength, boolean done);
}
