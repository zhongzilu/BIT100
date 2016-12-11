package com.zhongzilu.bit100.application.helper;

import com.zhongzilu.bit100.model.bean.PushModel;
import com.zhongzilu.bit100.model.bean.VideoBean;
import com.zhongzilu.bit100.view.adapter.MainRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * 动画视频帮助类
 * Created by zhongzilu on 16-12-1.
 */
public class AnimateHelper {

    public static ArrayList<PushModel> build(VideoBean[] array){
        ArrayList<PushModel> list = new ArrayList<>();
        for (VideoBean bean : array){
            list.add(AnimateHelper.build(bean));
        }
        return list;
    }

    public static PushModel build(VideoBean bean){
        return new PushModel(MainRecyclerViewAdapter.TYPE_MAIN_VIDEO_ITEM, bean);
    }

}
