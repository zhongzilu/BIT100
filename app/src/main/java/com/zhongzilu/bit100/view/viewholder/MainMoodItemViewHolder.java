package com.zhongzilu.bit100.view.viewholder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * 心情签名的ViewHolder
 * Created by zhongzilu on 2016-11-07.
 */
public class MainMoodItemViewHolder extends BaseViewHolder{

    //UI
    public TextView mMoodContent;
    public ImageView mMoodThumb;
    private Typeface mFace;

    public MainMoodItemViewHolder(View itemView, MyItemClickListener itemClickListener,
                                   MyItemLongClickListener longClickListener) {
        super(itemView, itemClickListener, longClickListener);

        mMoodContent = (TextView) itemView.findViewById(R.id.tv_mood_content);
        mMoodThumb = (ImageView) itemView.findViewById(R.id.img_mood_thumb);

        if (mFace == null)
            mFace = Typeface.createFromFile("file:///android_asset/font/FZYTK.ttf");
        mMoodContent.setTypeface(mFace);
    }
}
