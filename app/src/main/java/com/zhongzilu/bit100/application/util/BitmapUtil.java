package com.zhongzilu.bit100.application.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Bitmap工具类
 * Created by zhongzilu on 2016-11-10.
 */
public class BitmapUtil {

    public static final String IMAGE_SAVE_PATH =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + File.separator + "BIT100/";

    /**
     * 将Bitmap保存为JPEG格式的图片，保存到IMAGE_SAVE_PATH目录
     * @param bitmap
     *          要保存的bitmap
     * @return 返回图片保存的地址路径
     */
    public static String saveBitmap(Bitmap bitmap){
        return saveBitmap(bitmap, null);
    }

    /**
     * 将Bitmap保存为JPEG格式的图片，保存到IMAGE_SAVE_PATH目录
     * @param bitmap
     *          要保存的bitmap
     * @param name
     *          保存的文件名称，如果该参数为空，则使用当前的时间作为默认文件名
     * @return 返回图片保存的地址路径
     */
    public static String saveBitmap(Bitmap bitmap, String name){
        if (TextUtils.isEmpty(name)){
            String time = new SimpleDateFormat("yyyyMMddhhmmss", Locale.CHINA).format(new Date());
            name = time + ".jpg";
        } else {
            name += ".jpg";
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
    /**
     * 获取任意视图的Bitmap
     * @param view
     * @return View Bitmap
     */
    public static Bitmap getViewBitmap(View view){
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

}
