package com.example.background;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
