package com.zhongzilu.bit100.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * Created by zhongzilu on 2016-06-24.
 */
public class MainItemViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener{

    private MyItemClickListener mItemListener;
    private MyItemLongClickListener mLongListener;

    public MainItemViewHolder(View itemView, MyItemClickListener itemClickListener,
                              MyItemLongClickListener longClickListener) {
        super(itemView);

        this.mItemListener = itemClickListener;
        this.mLongListener = longClickListener;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mItemListener != null){
            mItemListener.onItemClick(this, v, getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mLongListener != null){
            mLongListener.onItemLongClick(this, v, getLayoutPosition());
        }
        return true;
    }
}
