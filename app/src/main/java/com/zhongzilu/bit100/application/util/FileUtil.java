package com.zhongzilu.bit100.application.util;

import android.os.Environment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * 文件工具类
 * Created by zhongzilu on 2016-07-28.
 */
public class FileUtil {

    public static final String LOGFILE = ".default.log";
    private static FileReader fr;
    private static FileWriter fw;
    public static final String FILESAVEPATH = Environment.getExternalStorageDirectory() + File.separator + "Mood/";
    public static final String IMGSAVEPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Mood/";

    public static void deleteFile(String paramString) {
        File localFile = new File(FILESAVEPATH + paramString);
        if (localFile.exists())
            localFile.delete();
    }

    public static String readFile(String paramString) {
        return "";
    }

    public static void save(String paramString) {
        save(LOGFILE, paramString, true);
    }

    public static void save(String paramString1, String paramString2, boolean paramBoolean) {

    }
}
