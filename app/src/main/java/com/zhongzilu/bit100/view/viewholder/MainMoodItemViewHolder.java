package com.zhongzilu.bit100.view.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.App;
import com.zhongzilu.bit100.application.util.ImageLoadUtil;
import com.zhongzilu.bit100.application.util.SharePreferenceUtil;
import com.zhongzilu.bit100.model.bean.CardMoodModel;
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

    public MainMoodItemViewHolder(View itemView, MyItemClickListener itemClickListener,
                                   MyItemLongClickListener longClickListener) {
        super(itemView, itemClickListener, longClickListener);

        mMoodContent = (TextView) itemView.findViewById(R.id.tv_mood_content);
        mMoodThumb = (ImageView) itemView.findViewById(R.id.img_mood_thumb);

        mMoodContent.setTypeface(App.getTypeface());
    }

    @Override
    public void bindValue(Context context, Object obj) {
        CardMoodModel bean = (CardMoodModel) obj;
        mMoodContent.setText(bean.mood_text);
        if (SharePreferenceUtil.isLoadImage()) {
            mMoodThumb.setVisibility(View.VISIBLE);
            ImageLoadUtil.loadImage(bean.mood_img, mMoodThumb);
        } else {
            mMoodThumb.setVisibility(View.GONE);
        }
    }
}
