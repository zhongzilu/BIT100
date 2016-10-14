package com.zhongzilu.bit100.application;

import android.app.Application;
import android.content.Context;

import com.zhongzilu.bit100.application.util.SharePreferenceUtil;

/**
 * Created by zhongzilu on 2016-09-16.
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        SharePreferenceUtil.config(this);
    }

    public static Context getAppContext(){
        return context;
    }
}
