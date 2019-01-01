package com.example.commontask.videocompressor;

import android.app.Application;

import com.example.commontask.videocompressor.file.FileUtils;

public class VideoCompressorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileUtils.createApplicationFolder();
    }

}