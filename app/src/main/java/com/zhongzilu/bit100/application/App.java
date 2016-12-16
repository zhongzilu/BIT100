package com.zhongzilu.bit100.application;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.receiver.NetworkBroadcastReceiver;
import com.zhongzilu.bit100.application.util.Check;
import com.zhongzilu.bit100.application.util.NetworkUtil;
import com.zhongzilu.bit100.application.util.SharePreferenceUtil;

/**
 * Created by zhongzilu on 2016-09-16.
 */
@SuppressWarnings("Range")
public class App extends Application {

    private static Context context;
    private static Typeface mFace;
    private NetworkBroadcastReceiver receiver;
    private static NetworkUtil.Type networkType;
    private static boolean isNetworkOK;
    private static Resources resource;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        resource = context.getResources();

        SharePreferenceUtil.config(this);
        NoHttp.initialize(this);
        Logger.setDebug(true);
        Logger.setTag("BIT100==>");

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkBroadcastReceiver();
//        registerReceiver(receiver, filter);


//        if (hasMemoryLeak()) {
//            refWatcher = LeakCanary.install(this);//预定义的 RefWatcher，同时也会启用一个 ActivityRefWatcher
//        }
//        if (hasCrashLog()) {
//            CrashWoodpecker.fly().to(this);//崩溃异常捕获
//        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // 程序结束时
//        unregisterReceiver(receiver);
        AppManager.getAppManager().AppExit(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // 内存回收时
//        unregisterReceiver(receiver);
    }

    public static synchronized Context getAppContext(){
        return context;
    }

    public static void setNetworkType(NetworkUtil.Type type){
        networkType = type;
        if (type == NetworkUtil.Type.NULL) {
            isNetworkOK = false;
            return;
        }
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

    public static Typeface getTypeface(){
        if (mFace == null)
            mFace = Typeface.createFromAsset(context.getAssets(), "font/FZYTK.ttf");
        return mFace;
    }

    /**
     * 根据资源返回String值
     *
     * @param id 资源id
     * @return String
     */
    public static String string(int id) {
        return resource.getString(id);
    }

    /**
     * 根据资源返回color值
     *
     * @param id 资源id
     * @return int类型的color
     */
    public static int color(int id) {
        return resource.getColor(id);
    }

    /**
     * 根据资源翻译Drawable值
     *
     * @param id 资源id
     * @return Drawable
     */
    public static Drawable drawable(int id) {
        return resource.getDrawable(id);
    }

    //======Snackbar
    public static Snackbar showSnackbar(@NonNull View view, @NonNull String message,
                                        @Snackbar.Duration int duration,
                                        @Nullable View.OnClickListener listener,
                                        @Nullable String actionStr) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        if (listener != null && Check.isEmpty(actionStr)) {
            snackbar.setAction(actionStr, listener);
        }
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showSnackbar(@NonNull View view, @NonNull int messageRes,
                                        @Snackbar.Duration int duration,
                                        @Nullable View.OnClickListener listener,
                                        @Nullable String actionStr) {
        Snackbar snackbar = Snackbar.make(view, messageRes, duration);
        if (listener != null && Check.isEmpty(actionStr)) {
            snackbar.setAction(actionStr, listener);
        }
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showSnackbar(@NonNull View view, @NonNull String message) {
        return showSnackbar(view, message, Snackbar.LENGTH_SHORT, null, null);
    }

    public static Snackbar showSnackbar(@NonNull View view, @StringRes int messageRes) {
        return showSnackbar(view, messageRes, Snackbar.LENGTH_SHORT, null, null);
    }

    public static Snackbar showSnackbar(@NonNull View view, @NonNull String message, @Nullable View.OnClickListener listener, @Nullable String actionStr) {
        return showSnackbar(view, message, Snackbar.LENGTH_SHORT, listener, actionStr);
    }

    public static Snackbar showSnackbar(@NonNull View view, @NonNull String message, @Nullable View.OnClickListener listener) {
        return showSnackbar(view, message, Snackbar.LENGTH_SHORT, listener, string(R.string.dialog_confirm_button_text));
    }

}
