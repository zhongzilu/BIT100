package com.zhongzilu.bit100.application.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhongzilu on 2016-09-17.
 */
public class SharePreferenceUtil {


    public static final String FILE_NAME = "Settings";

    public static final String IMG_PATH = "localImage";
    private static SharedPreferences.Editor editor;
    private static SharedPreferences share;

    public static void config(Context context) {
        share = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = share.edit();
        editor.apply();

    }

    public static void saveImagePath(String path) {

        editor.putString(IMG_PATH, path);
        editor.apply();
    }

    public static String getImagePath() {
        return share.getString(IMG_PATH, null);
    }
}
