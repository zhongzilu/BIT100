package com.zhongzilu.bit100.application.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhongzilu.bit100.application.util.NetworkUtil;

/**
 * 监听网络状态变化的广播接收器
 * Created by zhongzilu on 2016-10-24.
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkBroadcastReceiver==>";

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkUtil.getNetworkType(context);
    }

}
