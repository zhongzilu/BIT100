package com.zhongzilu.bit100.view.adapter.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhongzilu on 15-11-1.
 */
public interface MyItemClickListener {
    void onItemClick(RecyclerView.ViewHolder holder, View view, int position);
}