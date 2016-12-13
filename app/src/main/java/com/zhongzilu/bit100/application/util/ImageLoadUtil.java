package com.zhongzilu.bit100.application.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.App;

/**
 * Created by zhongzilu on 16-12-13.
 */
public class ImageLoadUtil {

    public static void loadImage(String url, ImageView imageView){
        Glide.with(App.getAppContext())
                .load(url)
                .placeholder(R.drawable.image_default)
                .error(R.drawable.big_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
