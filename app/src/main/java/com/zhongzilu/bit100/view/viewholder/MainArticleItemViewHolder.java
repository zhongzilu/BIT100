package com.zhongzilu.bit100.view.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.model.bean.TagBean;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * 主页模块所有文章列表ViewHolder
 * Created by zhongzilu on 2016-06-24.
 */
public class MainArticleItemViewHolder extends BaseViewHolder{
    private static final String TAG = "MainArticleItemViewHolder==>";

    //UI
    public TextView mAuthorName,mArticleTime,mArticleTitle;
    public ImageView mArticleThumb;
    public LinearLayout mArticleTagsWrapper;

    public MainArticleItemViewHolder(View itemView, MyItemClickListener itemClickListener,
                                     MyItemLongClickListener longClickListener) {
        super(itemView, itemClickListener, longClickListener);

        mAuthorName = (TextView) itemView.findViewById(R.id.tv_author_name);
        mArticleTime = (TextView) itemView.findViewById(R.id.tv_article_time);
        mArticleTitle = (TextView) itemView.findViewById(R.id.tv_article_title);
        mArticleThumb = (ImageView) itemView.findViewById(R.id.img_article_thumb);
        mArticleTagsWrapper = (LinearLayout) itemView.findViewById(R.id.layout_article_tags_wrapper);

        itemView.findViewById(R.id.img_article_share).setOnClickListener(this);
        itemView.findViewById(R.id.img_article_up).setOnClickListener(this);
    }

    public void initTag(){
        if (mArticleTagsWrapper != null){
            int childCount = mArticleTagsWrapper.getChildCount();
            mArticleTagsWrapper.removeViews(1, childCount - 1);
        }
    }

    public void addTag(Context context, TagBean[] tags){
        if (context == null) {
            LogUtil.d(TAG, "addTag: context is null");
            return;
        }

        initTag();

        for (TagBean t : tags) {
            addTag(context, t.title);
        }
    }

    public void addTag(Context context, String name){
        if (context == null) {
            LogUtil.d(TAG, "addTag: context is null");
            return;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 0, 0, 0);

        TextView textView = new TextView(context);
        textView.setText(name);
        textView.setPadding(4,2,4,2);
        textView.setTextSize(12);
        textView.setTextColor(context.getResources().getColor(R.color.hint));
        textView.setBackground(context.getResources().getDrawable(R.drawable.tag_border));
        textView.setLayoutParams(params);

        mArticleTagsWrapper.addView(textView);
    }
}
