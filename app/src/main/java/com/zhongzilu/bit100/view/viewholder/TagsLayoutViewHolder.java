package com.zhongzilu.bit100.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by zhongzilu on 16-9-17.
 */
public class TagsLayoutViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    public TagGroup mTagGroup;

    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;

    public TagsLayoutViewHolder(View itemView, MyItemClickListener itemClickListener,
                                   MyItemLongClickListener longClickListener) {
        super(itemView);

        mTagGroup = (TagGroup) itemView.findViewById(R.id.tag_group);

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

