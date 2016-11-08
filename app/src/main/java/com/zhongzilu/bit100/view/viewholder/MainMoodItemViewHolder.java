package com.zhongzilu.bit100.view.viewholder;

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
    public TextView mMoodName,mMoodTime, mMoodContent;
    public ImageView mMoodThumb;

    public MainMoodItemViewHolder(View itemView, MyItemClickListener itemClickListener,
                                  MyItemLongClickListener longClickListener) {
        super(itemView, itemClickListener, longClickListener);

        mMoodName = (TextView) itemView.findViewById(R.id.tv_mood_name);
        mMoodTime = (TextView) itemView.findViewById(R.id.tv_mood_time);
        mMoodContent = (TextView) itemView.findViewById(R.id.tv_mood_content);
        mMoodThumb = (ImageView) itemView.findViewById(R.id.img_mood_thumb);

        itemView.findViewById(R.id.img_mood_share).setOnClickListener(this);
        itemView.findViewById(R.id.img_mood_up).setOnClickListener(this);
    }
}
