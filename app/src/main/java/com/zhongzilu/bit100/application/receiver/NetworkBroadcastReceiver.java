package com.zhongzilu.bit100.application.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhongzilu.bit100.application.App;
import com.zhongzilu.bit100.application.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * 监听网络状态变化的广播接收器
 * Created by zhongzilu on 2016-10-24.
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkBroadcastReceiver==>";
    private static NetworkUtil.Type networkType;
    private static List<OnNetworkStateListener> list = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        networkType = NetworkUtil.getNetworkType(context);
        push();
    }

    private void push(){
        Observable
                .fromIterable(list)
                .subscribe(new Consumer<OnNetworkStateListener>() {
                    @Override
                    public void accept(OnNetworkStateListener onNetworkStateListener) throws Exception {
                        onNetworkStateListener.state(networkType);
                    }
                });
    }

    public static void subscribe(OnNetworkStateListener listener){
        list.add(listener);
    }

    public NetworkUtil.Type getNetworkType(){
        if (networkType == null){
            networkType = NetworkUtil.getNetworkType(App.getAppContext());
        }
        return networkType;
    }

    public interface OnNetworkStateListener{
        void state(NetworkUtil.Type type);
    }

}
