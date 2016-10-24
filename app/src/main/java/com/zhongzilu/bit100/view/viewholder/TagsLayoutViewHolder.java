package com.zhongzilu.bit100.view.viewholder;

import android.view.View;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

import me.gujun.android.taggroup.TagGroup;

/**
 * 文章标签ViewHolder
 * Created by zhongzilu on 16-9-17.
 */
public class TagsLayoutViewHolder extends BaseViewHolder {

    public TagGroup mTagGroup;

    public TagsLayoutViewHolder(View itemView, MyItemClickListener itemClickListener,
                                   MyItemLongClickListener longClickListener) {
        super(itemView,itemClickListener, longClickListener);

        mTagGroup = (TagGroup) itemView.findViewById(R.id.tag_group);

    }
}

