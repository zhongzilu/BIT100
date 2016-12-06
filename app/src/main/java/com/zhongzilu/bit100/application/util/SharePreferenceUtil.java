package com.zhongzilu.bit100.application.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhongzilu on 2016-09-17.
 */
public class SharePreferenceUtil {


    public static final String FILE_NAME = "Settings",
            LOADVIDEO = "LOADVIDEO",
            LOADHD = "LOADHD",
            NOIMAGE = "NOIMAGE",
            SHOW_NOTIFY = "SHOW_NOTIFY";

    public static final String IMG_PATH = "localImage";
    private static SharedPreferences.Editor editor;
    private static SharedPreferences share;

    public static void config(Context context) {
        share = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = share.edit();
        editor.apply();

    }

    public static void setLoadVideoOnWifi(final boolean isLoad){
        new Thread(new Runnable() {
            @Override
            public void run() {
                editor.putBoolean(LOADVIDEO, isLoad).apply();
            }
        }).start();
    }

    /**获取是否加载在Wifi下加载视频的设置，默认为true*/
    public static boolean isLoadVideoOnWifi(){
        return share.getBoolean(LOADVIDEO, true);
    }

    public static void setLoadHDVideo(final boolean isLoad){
        new Thread(new Runnable() {
            @Override
            public void run() {
                editor.putBoolean(LOADHD, isLoad).apply();
            }
        }).start();
    }

    /**获取是否加载高清视频的设置，默认为true*/
    public static boolean isLoadHD(){
        return share.getBoolean(LOADHD, true);
    }

    public static void setLoadImage(final boolean isImage){
        new Thread(new Runnable() {
            @Override
            public void run() {
                editor.putBoolean(NOIMAGE, isImage).apply();
            }
        }).start();
    }

    /**获取是否不加载图片的设置，默认为false*/
    public static boolean isLoadImage(){
        return share.getBoolean(NOIMAGE, true);
    }

    public static void setShowNotify(final boolean isShow){
        new Thread(new Runnable() {
            @Override
            public void run() {
                editor.putBoolean(SHOW_NOTIFY, isShow).apply();
            }
        }).start();
    }

    /**获取是否显示通知的设置，默认为true*/
    public static boolean isShowNotify(){
        return share.getBoolean(SHOW_NOTIFY, true);
    }

    public static void saveImagePath(String path) {

        editor.putString(IMG_PATH, path);
        editor.apply();
    }

    public static String getImagePath() {
        return share.getString(IMG_PATH, null);
    }
}
