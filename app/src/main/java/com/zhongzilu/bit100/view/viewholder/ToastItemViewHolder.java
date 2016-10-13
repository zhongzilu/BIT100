package com.zhongzilu.bit100.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * Created by zhongzilu on 2016-09-16.
 */
public class ToastItemViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {


    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;

    public ToastItemViewHolder(View itemView, MyItemClickListener itemClickListener,
                               MyItemLongClickListener longClickListener) {
        super(itemView);


        this.myItemClickListener = itemClickListener;
        this.myItemLongClickListener = longClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (myItemClickListener != null) {
            myItemClickListener.onItemClick(this, v, getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (myItemLongClickListener != null) {
            myItemLongClickListener.onItemLongClick(this, v, getLayoutPosition());
        }
        return true;
    }
}
