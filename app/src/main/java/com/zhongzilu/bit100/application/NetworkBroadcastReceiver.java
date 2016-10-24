package com.zhongzilu.bit100.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 监听网络状态变化的广播接收器
 * Created by zhongzilu on 2016-10-24.
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkBroadcastReceiver==>";

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取手机的连接服务管理器，这里是连接管理器类
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Network[] states = cm.getAllNetworks();
//
//            if (states != null){
//                for (Network work : states){
//                    NetworkInfo networkInfo = cm.getNetworkInfo(work);
//
//                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI &&
//                            networkInfo.getState().equals(NetworkInfo.State.CONNECTED )){
//
//                        return;
//                    }
//                }
//
//                //wifi和数据流量都没有打开，提示没有可用网络，引导用户打开任意网络
//            }
//
//            return;
//        }

        NetworkInfo.State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo.State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State mobile4G = cm.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).getState();

        if (wifiState == null && mobileState == null){
            //wifi和数据流量都没有打开，提示没有可用网络，引导用户打开任意网络
            return;
        }

        if (NetworkInfo.State.CONNECTED == wifiState){
            //wifi连接可用，这时可以加载比较耗流量的内容，比如更高清的图片和视频等
            return;
        }

        if (NetworkInfo.State.CONNECTED == mobile4G){
            //4G网络可用，这时可以加载比较耗流量的内容，同时也通过缓存机制和其他机制
            //优化网络请求频率以及数据加载以减少流量消耗
            return;
        }

        if (NetworkInfo.State.CONNECTED == mobileState){
            //数据流量可用，这时避免加载比较耗流量的内容，同时，可以通过缓存机制和其他机制
            //优化网络请求频率以及数据加载以减少流量消耗
//            return;
        }

    }
}
