package com.zhongzilu.bit100.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.model.bean.ArticleDetailBean;
import com.zhongzilu.bit100.model.bean.CardMoodModel;
import com.zhongzilu.bit100.model.bean.PushModel;
import com.zhongzilu.bit100.view.viewholder.MainArticleItemViewHolder;
import com.zhongzilu.bit100.view.viewholder.MainMoodItemViewHolder;

import java.util.ArrayList;

/**
 * 【主页】模块的RecyclerViewAdapter
 * Created by zhongzilu on 2016-10-24.
 */
public class MainRecyclerViewAdapter extends BaseRecyclerViewAdapter {
    private static final String TAG = "MainRecyclerViewAdapter==>";

    //主页item布局类型
    public static final int TYPE_MAIN_ARTICLE_ITEM = 100;
    public static final int TYPE_MAIN_MOOD_ITEM = 101;

    public MainRecyclerViewAdapter(Context context, ArrayList<PushModel> mPushList) {
        super(context, mPushList);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_MAIN_ARTICLE_ITEM:
                //普通条目
                view = LayoutInflater.from(context).inflate(R.layout.item_artical_list, parent, false);
                return new MainArticleItemViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_MAIN_MOOD_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.item_mood_list, parent, false);
                return new MainMoodItemViewHolder(view, mItemClickListener, mItemLongClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        switch (getItemViewType(position)) {

            case TYPE_MAIN_ARTICLE_ITEM:
                ArticleDetailBean articleDetailBean = (ArticleDetailBean) mPushList.get(position).getPushObject();
                MainArticleItemViewHolder mainArticleItemViewHolder = (MainArticleItemViewHolder) holder;
                mainArticleItemViewHolder.mAuthorName.setText(articleDetailBean.author.nickname);
                mainArticleItemViewHolder.mArticleTime.setText(articleDetailBean.date);
                mainArticleItemViewHolder.mArticleTitle.setText(articleDetailBean.title);

                // zhongzilu: 2016-10-21 如果存在缩略图，则加载缩略图
                if (articleDetailBean.thumbnail_images != null) {
                    Glide.with(context)
                            .load(articleDetailBean.thumbnail_images.medium.url)
                            .into(mainArticleItemViewHolder.mArticleThumb);
                    mainArticleItemViewHolder.mArticleThumb.setVisibility(View.VISIBLE);
                } else {
                    mainArticleItemViewHolder.mArticleThumb.setVisibility(View.GONE);
                }
                // zhongzilu: 2016-10-21 加载标签
                mainArticleItemViewHolder.addTag(context, articleDetailBean.tags);
                break;
            case TYPE_MAIN_MOOD_ITEM:
                CardMoodModel cardMoodModel = (CardMoodModel) mPushList.get(position).getPushObject();
                MainMoodItemViewHolder mainMoodItemViewHolder = (MainMoodItemViewHolder) holder;

                mainMoodItemViewHolder.mMoodName.setText(cardMoodModel.type_name);
                mainMoodItemViewHolder.mMoodTime.setText(cardMoodModel.mood_date);
                mainMoodItemViewHolder.mMoodContent.setText(cardMoodModel.mood_text);
                if (!TextUtils.isEmpty(cardMoodModel.mood_img)) {
                    Glide.with(context)
                    .load(cardMoodModel.mood_img)
                    .into(mainMoodItemViewHolder.mMoodThumb);
                    mainMoodItemViewHolder.mMoodThumb.setVisibility(View.VISIBLE);
                } else {
                    mainMoodItemViewHolder.mMoodThumb.setVisibility(View.GONE);
                }
                break;
        }
    }
}
