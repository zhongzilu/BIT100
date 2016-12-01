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

//    public static ArrayList<VideoBean> build(JSONArray array){
//        ArrayList<VideoBean> list = new ArrayList<>();
//        for (int i=0; i<array.length(); i++){
//            AnimateHelper.build(array.getJSONObject(i));
//        }
//        return list;
//    }
//
//    public static VideoBean build(JSONObject animate){
//        String id = getValue(animate, "Id");
//        String name = getValue(animate, "Name");
//        String duration = getValue(animate, "Duration");
//        String videoSite = getValue(animate, "VideoSite");
//        String originVideoUrl = getValue(animate, "VideoUrl");
//        String author = getValue(animate, "Author");
//        String year = getValue(animate, "Year");
//        String brief = getValue(animate, "Brief");
//        String homePic = getValue(animate, "HomePic");
//        String detailPic = getValue(animate, "DetailPic");
//        JSONObject videoSourceObject = null;
//        try {
//            videoSourceObject = animate.getJSONObject("VideoSource");
//            String uhd = videoSourceObject.has("uhd") ? videoSourceObject.getString("uhd") : null;
//            String hd = videoSourceObject.has("hd") ? videoSourceObject.getString("hd") : null;
//            String sd = videoSourceObject.has("sd") ? videoSourceObject.getString("sd") : null;
//            uhd = uhd.replace(";", "&");
//            hd = uhd.replace(";", "&");
//            sd = uhd.replace(";", "&");
//        } catch (JSONException e) {
////            e.printStackTrace();
//        } finally {
//            return new VideoBean(id, author, year, duration, originVideoUrl, )
//        }
//
//    }
//
//    public static String getValue(JSONObject obj, String name){
//        try {
//            obj.getString(name);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
