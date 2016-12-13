package com.zhongzilu.bit100.view.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.model.bean.IconItemDecoratorBean;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * 带有图标的分割栏ViewHolder
 * Created by zhongzilu on 2016-09-16.
 */
public class IconDecoratorViewHolder extends BaseViewHolder {

    public ImageView mIcon;
    public TextView mName;

    public IconDecoratorViewHolder(View itemView, MyItemClickListener itemClickListener,
                                     MyItemLongClickListener longClickListener) {
        super(itemView, itemClickListener, longClickListener);

        mIcon = (ImageView) itemView.findViewById(R.id.img_decorator_icon);
        mName = (TextView) itemView.findViewById(R.id.tv_decorator_text);

    }

    @Override
    public void bindValue(Context context, Object obj) {
        IconItemDecoratorBean bean = (IconItemDecoratorBean) obj;
        mIcon.setImageResource(bean.getmIconRes());
        mName.setText(bean.getmTitle());
    }

}
