package com.zhongzilu.bit100.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

/**
 * Created by zhongzilu on 16-12-1.
 */
public class VideoViewHolder extends BaseViewHolder {

    public ImageView mVideoImage;
    public TextView mVideoName,
            mVideoContent,
            mVideoAuthor,
            mVideoDuration;

    public VideoViewHolder(View itemView, MyItemClickListener itemClickListener,
                           MyItemLongClickListener longClickListener) {
        super(itemView, itemClickListener, longClickListener);

        mVideoImage = (ImageView) itemView.findViewById(R.id.img_video_image);
        mVideoName = (TextView) itemView.findViewById(R.id.tv_video_title);
        mVideoContent = (TextView) itemView.findViewById(R.id.tv_video_content);
        mVideoAuthor = (TextView) itemView.findViewById(R.id.tv_video_author);
        mVideoDuration = (TextView) itemView.findViewById(R.id.tv_video_duration);
    }
}
