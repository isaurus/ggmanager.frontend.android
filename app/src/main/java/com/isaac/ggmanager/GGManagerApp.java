package com.isaac.ggmanager;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class GGManagerApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
    }
}