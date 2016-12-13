package com.zhongzilu.bit100.view.viewholder;

import android.content.Context;
import android.view.View;

import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * 通知内容布局ViewHolder
 * Created by zhongzilu on 2016-09-16.
 */
public class ToastItemViewHolder extends BaseViewHolder {
    private static final String TAG = "ToastItemViewHolder==>";

    public ToastItemViewHolder(View itemView, MyItemClickListener itemClickListener,
                               MyItemLongClickListener longClickListener) {
        super(itemView, itemClickListener, longClickListener);

    }

    @Override
    public void bindValue(Context context, Object obj) {

    }
}
