package com.yjj.flutter.inandroid;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.CallSuper;

import io.flutter.view.FlutterMain;

/**
 * created by yangjianjun on 2019/4/7
 * 入口application
 */
public class MyApplication extends Application {
    private Activity mCurrentActivity = null;

    public MyApplication() {
    }

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        FlutterMain.startInitialization(this);
    }

    public Activity getCurrentActivity() {
        return this.mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }
}
