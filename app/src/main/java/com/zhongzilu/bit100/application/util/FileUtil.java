package com.zhongzilu.bit100.application.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    /**
     * 将Bitmap保存为JPEG格式的图片，保存到IMAGE_SAVE_PATH目录
     * @param bitmap
     *          要保存的bitmap
     * @return 返回图片保存的地址路径
     */
    public static String saveImage(Bitmap bitmap){
        return saveImage(bitmap, null);
    }

    /**
     * 将Bitmap保存为JPEG格式的图片，保存到IMAGE_SAVE_PATH目录
     * @param bitmap
     *          要保存的bitmap
     * @param name
     *          保存的文件名称，如果该参数为空，则使用当前的时间作为默认文件名
     * @return 返回图片保存的地址路径
     */
    public static String saveImage(Bitmap bitmap, String name){
        if (TextUtils.isEmpty(name)){
            String time = new SimpleDateFormat("yyyyMMddhhmmss", Locale.CHINA).format(new Date());
            name = time + ".jpg";
        }

        File file = new File(IMAGE_SAVE_PATH);

        if (!file.exists())
            file.mkdirs();

        try {
            file = new File(IMAGE_SAVE_PATH, name);
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getPath();

    }

}
