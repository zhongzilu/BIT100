package com.zhongzilu.bit100.application.helper;

import android.content.Context;
import android.os.Environment;

import com.zhongzilu.bit100.application.util.FileUtil;

import java.math.BigDecimal;

/**
 * 缓存清理帮助类
 * Created by zhongzilu on 16-11-29.
 */
public class CacheCleanHelper {

    /**清除所有缓存文件*/
    public static void cleanAllCache(final Context context){
        new Thread(() -> {
            FileUtil.deleteFile(context.getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                FileUtil.deleteFile(context.getExternalCacheDir());
            }
        }).start();
    }



    /**获取总共缓存大小*/
    public static String getTotalCacheSize(Context context){
        long size = FileUtil.getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            size += FileUtil.getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(size);
    }

    /**
     * 格式化单位
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            return countDecimal(kiloByte) + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            return countDecimal(megaByte) + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            return countDecimal(gigaByte) + "GB";
        }
        return countDecimal(teraBytes) + "TB";
    }

    /**精确计算文件大小数值*/
    private static String countDecimal(double bytes){
        return new BigDecimal(Double.toString(bytes))
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString();
    }
}
