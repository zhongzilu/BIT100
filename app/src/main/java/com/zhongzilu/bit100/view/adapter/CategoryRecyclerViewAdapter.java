package com.zhongzilu.bit100.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.model.bean.PushModel;
import com.zhongzilu.bit100.view.viewholder.CategoryViewHolder;
import com.zhongzilu.bit100.view.viewholder.TagsLayoutViewHolder;

import java.util.ArrayList;

/**
 * 【分类】模块的RecyclerViewAdapter
 * Created by zhongzilu on 2016-10-24.
 */
public class CategoryRecyclerViewAdapter extends BaseRecyclerViewAdapter {
    private static final String TAG = "CategoryRecyclerViewAdapter==>";

    //分类item布局类型
    public static final int TYPE_TAGS = 110;
    public static final int TYPE_CATEGORY = 111;

    public CategoryRecyclerViewAdapter(Context context, ArrayList<PushModel> mPushList) {
        super(context, mPushList);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_CATEGORY:
                //目录item布局
                view = LayoutInflater.from(context).inflate(R.layout.item_category_layout, parent, false);
                return new CategoryViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_TAGS:
                //标签布局
                view = LayoutInflater.from(context).inflate(R.layout.item_tags_layout, parent, false);
                return new TagsLayoutViewHolder(view, mItemClickListener, mItemLongClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        switch (getItemViewType(position)){
            case TYPE_CATEGORY:
                ((CategoryViewHolder) holder)
                        .bindValue(context, mPushList.get(position).getPushObject());
                break;

            case TYPE_TAGS:
                ((TagsLayoutViewHolder) holder)
                        .bindValue(context, mPushList.get(position).getPushObject());
                break;
        }
    }
}
