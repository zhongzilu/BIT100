package com.zhongzilu.bit100.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.model.bean.ArticleDetailBean;
import com.zhongzilu.bit100.model.bean.PushModel;
import com.zhongzilu.bit100.view.viewholder.MainItemViewHolder;

import java.util.ArrayList;

/**
 * 【主页】模块的RecyclerViewAdapter
 * Created by zhongzilu on 2016-10-24.
 */
public class MainRecyclerViewAdapter extends BaseRecyclerViewAdapter {
    private static final String TAG = "MainRecyclerViewAdapter==>";

    //主页item布局类型
    public static final int TYPE_MAIN_ITEM = 100;

    public MainRecyclerViewAdapter(Context context, ArrayList<PushModel> mPushList) {
        super(context, mPushList);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_MAIN_ITEM:
                //普通条目
                view = LayoutInflater.from(context).inflate(R.layout.item_artical_list, parent, false);
                return new MainItemViewHolder(view, mItemClickListener, mItemLongClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        switch (getItemViewType(position)) {

            case TYPE_MAIN_ITEM:
                ArticleDetailBean articleDetailBean = (ArticleDetailBean) mPushList.get(position).getPushObject();
                MainItemViewHolder mainItemViewHolder = (MainItemViewHolder) holder;
                mainItemViewHolder.mAuthorName.setText(articleDetailBean.author.nickname);
                mainItemViewHolder.mArticleTime.setText(articleDetailBean.date);
                mainItemViewHolder.mArticleTitle.setText(articleDetailBean.title);

                // zhongzilu: 2016-10-21 如果存在缩略图，则加载缩略图
                if (articleDetailBean.thumbnail_images != null) {
                    Glide.with(context)
                            .load(articleDetailBean.thumbnail_images.medium.url)
                            .into(mainItemViewHolder.mArticleThumb);
                    mainItemViewHolder.mArticleThumb.setVisibility(View.VISIBLE);
                } else {
                    mainItemViewHolder.mArticleThumb.setVisibility(View.GONE);
                }
                // zhongzilu: 2016-10-21 加载标签
                mainItemViewHolder.addTag(context, articleDetailBean.tags);

                break;
        }
    }
}
