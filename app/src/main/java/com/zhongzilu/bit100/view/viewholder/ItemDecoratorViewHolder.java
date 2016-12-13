package com.zhongzilu.bit100.view.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.model.bean.ItemDecoratorBean;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * 不带图标的分割栏ViewHolder,该分割栏共有两个文字控件
 * 一个是主标题mTitle，一个是副标题mSubTitle,主标题文字
 * 大小为14sp，副标题文字大小为12sp
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

    @Override
    public void bindValue(Context context, Object obj) {
        ItemDecoratorBean bean = (ItemDecoratorBean) obj;
        mTitle.setText(bean.getmTitle());
        mSubTitle.setText(bean.getmSubTitle());
    }
}
