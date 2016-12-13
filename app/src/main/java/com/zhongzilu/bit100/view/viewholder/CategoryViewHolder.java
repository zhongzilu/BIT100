package com.zhongzilu.bit100.view.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.model.bean.CategoriesBean;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * 目录分类ViewHolder
 * Created by zhongzilu on 2016-09-17.
 */
public class CategoryViewHolder extends BaseViewHolder{
    private static final String TAG = "CategoryViewHolder==>";

    public TextView mName, mPostCount;

    public CategoryViewHolder(View itemView, MyItemClickListener itemClickListener,
                              MyItemLongClickListener longClickListener) {
        super(itemView, itemClickListener, longClickListener);

        mName = (TextView) itemView.findViewById(R.id.tv_category_name);
        mPostCount = (TextView) itemView.findViewById(R.id.tv_category_post_count);
    }

    @Override
    public void bindValue(Context context, Object obj) {
        if (obj == null){
            LogUtil.d(TAG, "bindValue: categories bean is null");
            return;
        }
        CategoriesBean bean = (CategoriesBean) obj;
        mName.setText(bean.title);
        mPostCount.setText(bean.post_count);
    }
}

