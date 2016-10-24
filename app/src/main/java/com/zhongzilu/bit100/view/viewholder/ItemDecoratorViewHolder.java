package com.zhongzilu.bit100.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * Created by zhongzilu on 2016-09-16.
 */
public class ItemDecoratorViewHolder extends BaseViewHolder{

    public TextView mTitle, mSubTitle;

    public ItemDecoratorViewHolder(View itemView, MyItemClickListener itemClickListener,
                                               MyItemLongClickListener longClickListener) {
        super(itemView, itemClickListener, longClickListener);

        mTitle = (TextView) itemView.findViewById(R.id.tv_item_decorator_title);
        mSubTitle = (TextView) itemView.findViewById(R.id.tv_item_decorator_subtitle);

    }
}
