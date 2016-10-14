package com.zhongzilu.bit100.application.util;

import android.util.Log;

/**
 * 日志工具类
 * Created by zhongzilu on 2016-07-28.
 */
public class LogUtil {

    private static String TAG = "LogUtil==>";
    private static final int VERBOSE = 0x001;
    private static final int DEBUG = 0x002;
    private static final int INFO = 0x003;
    private static final int WARE = 0x004;
    private static final int ERROR = 0x005;
    private static final int NOTHING = 0x006;

    private static int LEVEL = VERBOSE;

    public static void v(String msg){
        if (LEVEL <= VERBOSE)
            Log.v(TAG, msg);
    }

    public static void v(String tag, String msg){
        if (LEVEL <= VERBOSE)
            Log.v(tag, msg);
    }

    public static void d(String msg){
        if (LEVEL <= DEBUG)
            Log.d(TAG, msg);
    }

    public static void d(String tag, String msg){
        if (LEVEL <= DEBUG)
            Log.d(tag, msg);
    }

    public static void i(String msg){
        if (LEVEL <= INFO)
            Log.i(TAG, msg);
    }

    public static void i(String tag, String msg){
        if (LEVEL <= INFO)
            Log.i(tag, msg);
    }

    public static void w(String msg){
        if (LEVEL <= WARE)
            Log.w(TAG, msg);
    }

    public static void w(String tag, String msg){
        if (LEVEL <= WARE)
            Log.w(tag, msg);
    }

    public static void e(String msg){
        if (LEVEL <= ERROR)
            Log.e(TAG, msg);
    }

    public static void e(String tag, String msg){
        if (LEVEL <= ERROR)
            Log.e(tag, msg);
    }

}
