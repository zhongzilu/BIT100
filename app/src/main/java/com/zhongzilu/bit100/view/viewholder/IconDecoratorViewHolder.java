package com.zhongzilu.bit100.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * Created by zhongzilu on 2016-09-16.
 */
public class IconDecoratorViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    public ImageView mIcon;
    public TextView mName;

    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;

    public IconDecoratorViewHolder(View itemView, MyItemClickListener itemClickListener,
                                     MyItemLongClickListener longClickListener) {
        super(itemView);

        mIcon = (ImageView) itemView.findViewById(R.id.img_decorator_icon);
        mName = (TextView) itemView.findViewById(R.id.tv_decorator_text);

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
