package com.zhongzilu.bit100.application.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * 屏幕密度工具类
 * Created by zhongzilu on 2016-07-28.
 */
public class Density {

    public static int dp2px(Context paramContext, float paramFloat) {
        return Math.round(TypedValue.applyDimension(1, paramFloat, paramContext.getResources().getDisplayMetrics()));
    }
}
