package com.zhongzilu.bit100.application;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.zhongzilu.bit100.application.receiver.NetworkBroadcastReceiver;
import com.zhongzilu.bit100.application.util.NetworkUtil;
import com.zhongzilu.bit100.application.util.SharePreferenceUtil;

/**
 * Created by zhongzilu on 2016-09-16.
 */
public class App extends Application {

    private static Context context;
    private NetworkBroadcastReceiver receiver;
    private static NetworkUtil.Type networkType;
    private static boolean isNetworkOK;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        SharePreferenceUtil.config(this);
        NoHttp.initialize(this);
        Logger.setDebug(false);
        Logger.setTag("BIT100==>");

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkBroadcastReceiver();
        //registerReceiver(receiver, filter);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // 程序结束时
        unregisterReceiver(receiver);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // 内存回收时
        unregisterReceiver(receiver);
    }

    public static Context getAppContext(){
        return context;
    }

    public static void setNetworkType(NetworkUtil.Type type){
        if (type == NetworkUtil.Type.NULL) {
            isNetworkOK = false;
            return;
        }
        networkType = type;
        isNetworkOK = true;
    }

    public static void setNetworkOK(boolean isOk){
        isNetworkOK = isOk;
    }

    public static NetworkUtil.Type getNetworkType() {
        return networkType;
    }

    public static boolean getNetworkStatus(){
        return isNetworkOK;
    }


}
