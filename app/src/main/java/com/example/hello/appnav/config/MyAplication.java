package com.example.hello.appnav.config;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class MyAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
