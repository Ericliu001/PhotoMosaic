package com.example.ericliu.photomosaic;

import android.app.Application;

/**
 * Created by ericliu on 30/07/2016.
 */

public class MyApplication extends Application {
    public static MyApplication myApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        if (myApplication == null) {
            myApplication = this;
        }
    }
}
