package com.zhongzilu.bit100.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.model.bean.PushModel;
import com.zhongzilu.bit100.view.viewholder.MainArticleItemViewHolder;
import com.zhongzilu.bit100.view.viewholder.MainMoodItemViewHolder;
import com.zhongzilu.bit100.view.viewholder.VideoViewHolder;

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
    public static final int TYPE_MAIN_VIDEO_ITEM = 102;

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
                //心情签名
                view = LayoutInflater.from(context).inflate(R.layout.item_mood_list, parent, false);
                return new MainMoodItemViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_MAIN_VIDEO_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.item_video_layout, parent, false);
                return new VideoViewHolder(view, mItemClickListener, mItemLongClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        switch (getItemViewType(position)) {

            case TYPE_MAIN_ARTICLE_ITEM:
                ((MainArticleItemViewHolder) holder)
                        .bindValue(context, mPushList.get(position).getPushObject());
                break;
            case TYPE_MAIN_MOOD_ITEM:
                ((MainMoodItemViewHolder) holder)
                        .bindValue(context, mPushList.get(position).getPushObject());
                break;

            case TYPE_MAIN_VIDEO_ITEM:
                ((VideoViewHolder) holder)
                        .bindValue(context, mPushList.get(position).getPushObject());
                break;
        }
    }

}
