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
    public static final String FILE_SAVE_PATH = Environment.getExternalStorageDirectory() + File.separator + "BIT100/";
    public static final String IMAGE_SAVE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "BIT100/";

    public static void deleteFile(String paramString) {
        File localFile = new File(FILE_SAVE_PATH + paramString);
        if (localFile.exists())
            localFile.delete();
    }

    private static String readFile(String paramString) {
        return "";
    }

    private static void save(String paramString) {
        save(LOGFILE, paramString, true);
    }

    private static void save(String paramString1, String paramString2, boolean paramBoolean) {

    }

}
