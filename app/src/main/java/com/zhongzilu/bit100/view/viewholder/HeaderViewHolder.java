package com.zhongzilu.bit100.view.viewholder;

import android.view.View;

import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * RecyclerView的头部视图ViewHolder
 * Created by zhongzilu on 2016-09-16.
 */
public class HeaderViewHolder extends BaseViewHolder{

    public HeaderViewHolder(View itemView, MyItemClickListener itemClickListener,
                            MyItemLongClickListener longClickListener) {
        super(itemView, itemClickListener, longClickListener);
    }
}
