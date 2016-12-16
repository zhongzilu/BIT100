package com.zhongzilu.bit100.application.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.zhongzilu.bit100.application.App;

/**
 * 网络工具类，用于检查网络是否可用，获取当前链接网络的类型等信息
 * Created by zhongzilu on 2016-10-31.
 */
public class NetworkUtil {

    /**
     * 检查当前网络是否可用
     * @return 返回true/false，true表示有可用的网络，false表示无可用的网络
     */
    public static boolean getNetworkState() {
        return getNetworkType(App.getAppContext()) != Type.NULL;
    }

    /**
     * 获取当前网络的类型
     * @return 返回值包括Type.NULL，Type.WIFI, Type.MOBILE4G, Type.MOBILE
     */
    public static Type getNetworkType(Context context){
        //获取手机的连接服务管理器，这里是连接管理器类
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return checkNetWorkStateOnLollipop(cm);
        }

        return checkNetworkState(cm);
    }

    /**
     * 检查网络状态,该方法用于Android 5.0以下
     * @param cm ConnectivityManager
     * @return 返回网络类型
     */
    public static Type checkNetworkState(ConnectivityManager cm){
        NetworkInfo.State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo.State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
//        NetworkInfo.State mobile4G = cm.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).getState();

        if (wifiState == null && mobileState == null) {
            //wifi和数据流量都没有打开，提示没有可用网络，引导用户打开任意网络
            App.setNetworkType(Type.NULL);
            return Type.NULL;
        }

        if (NetworkInfo.State.CONNECTED == wifiState) {
            //wifi连接可用，这时可以加载比较耗流量的内容，比如更高清的图片和视频等
            App.setNetworkType(Type.WIFI);
            return Type.WIFI;
        }

//        if (NetworkInfo.State.CONNECTED == mobile4G) {
//            //4G网络可用，这时可以加载比较耗流量的内容，同时也通过缓存机制和其他机制
//            //优化网络请求频率以及数据加载以减少流量消耗
//            App.setNetworkType(Type.MOBILE4G);
//            return Type.MOBILE4G;
//        }

        if (NetworkInfo.State.CONNECTED == mobileState) {
            //数据流量可用，这时避免加载比较耗流量的内容，同时，可以通过缓存机制和其他机制
            //优化网络请求频率以及数据加载以减少流量消耗
            App.setNetworkType(Type.MOBILE);
            return Type.MOBILE;
        }
        return Type.NULL;
    }

    /**
     * 检查网络状态,该方法用于Android 5.0以上
     * @param cm ConnectivityManager
     * @return 返回网络类型
     */
    public static Type checkNetWorkStateOnLollipop(ConnectivityManager cm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] states = cm.getAllNetworks();
            if (states != null) {
                for (Network work : states) {
                    NetworkInfo networkInfo = cm.getNetworkInfo(work);
                    if (networkInfo.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                        //wifi和数据流量都没有打开，提示没有可用网络，引导用户打开任意网络
                        App.setNetworkType(Type.NULL);
                        return Type.NULL;
                    }
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI &&
                            networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                        App.setNetworkType(Type.WIFI);
                        return Type.WIFI;
                    }
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIMAX &&
                            networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                        App.setNetworkType(Type.MOBILE4G);
                        return Type.MOBILE4G;
                    }

                    if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE &&
                            networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                        App.setNetworkType(Type.MOBILE);
                        return Type.MOBILE;
                    }
                }
            }
        }
        return Type.NULL;
    }

    public enum Type {
        NULL, WIFI, MOBILE4G, MOBILE
    }
}
