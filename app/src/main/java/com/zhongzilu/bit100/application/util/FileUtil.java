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

    public static void deleteFile(String fileName) {
        File localFile = new File(FILE_SAVE_PATH + fileName);
        if (localFile.exists())
            localFile.delete();
    }

    public static boolean deleteFile(File file){
        if (file == null)
            throw new NullPointerException("Parameter file is null");
        if (file.isDirectory()){
            deleteDirectory(file);
        }

        return file.delete();
    }

    public static boolean deleteDirectory(File directory){
        if (!directory.isDirectory()){
            throw new IllegalArgumentException("Parameter file is not a directory!");
        }
        File[] files = directory.listFiles();
        for (File f : files){
            if (!deleteFile(f)){
                return false;
            }
        }
        return directory.delete();
    }

    private static String readFile(String paramString) {
        return "";
    }

    private static void save(String paramString) {
        save(LOGFILE, paramString, true);
    }

    private static void save(String paramString1, String paramString2, boolean paramBoolean) {

    }

    /**获取目录下所有文件的总大小*/
    public static long getFolderSize(File folder){
        long size = 0L;
        try {

            File[] fileArray = folder.listFiles();
            for (File f : fileArray){
                if (f.isDirectory()){
                    size += getFolderSize(f);
                } else {
                    size += f.length();
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }

}
