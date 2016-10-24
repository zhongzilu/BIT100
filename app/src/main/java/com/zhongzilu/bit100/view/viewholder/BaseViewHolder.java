package com.zhongzilu.bit100.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * ViewHolder的基类，
 * Created by zhongzilu on 2016-10-24.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener{

    protected MyItemClickListener myItemClickListener;
    protected MyItemLongClickListener myItemLongClickListener;

    public BaseViewHolder(View itemView, MyItemClickListener itemClickListener,
                          MyItemLongClickListener longClickListener) {
        super(itemView);

        this.myItemClickListener = itemClickListener;
        this.myItemLongClickListener = longClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (myItemClickListener != null){
            myItemClickListener.onItemClick(this, v, getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (myItemLongClickListener != null){
            myItemLongClickListener.onItemLongClick(this, v, getLayoutPosition());
        }
        return true;
    }
}
