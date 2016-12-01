package com.zhongzilu.bit100.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhongzilu.bit100.model.bean.VideoBean;
import com.zhongzilu.bit100.model.bean.VideoCategoryBean;

/**
 * 初始化时请求的视频数据响应类
 * {@注意：}
 * 这里只是取了list字段的数据，其他的请自行获取或判断
 * Created by zhongzilu on 16-12-1.
 */
public class VideoResponse implements Parcelable{

/**
 {
     "data": {
         "result": "true",
         "list": {
             "anime":[
                 {
                 "Id": "1518",
                 "Author": "Steve Scott",
                 "Year": "2014",
                 "Duration": "01:23",
                 "VideoUrl": "http://v.youku.com/v_show/id_XODM5MDAyNTM2.html",
                 "VideoSite": "youku",
                 "Brief": "John Lobb，一位来自英国的顶级鞋设计师，一个堪称手工定制鞋之王的高端品牌。这是伦敦动画导演Steve Scott为英国著名皮鞋品牌John Lobb制作的广告~整个片子的色彩明亮，非常可爱。",
                 "HomePic": "http://ww2.sinaimg.cn/large/0066P23Wjw1ern9dnllhqj30340340t8.jpg",
                 "DetailPic": "http://ww1.sinaimg.cn/large/0066P23Wjw1ern9dsn0cvj30hs07sta3.jpg",
                 "Player": "http://i.animetaste.net/player/?id=5620",
                 "ShareLink": "http://aimozhen.com/view/5620/",
                 "Name": "Time’s the Charm(83)",
                 "VideoSource": {
                     "uhd": "http://i.animetaste.net/m3u8?originId=XODM5MDAyNTM2&id=5620",
                     "hd": "http://i.animetaste.net/m3u8?originId=XODM5MDAyNTM2&id=5620",
                     "sd": "http://i.animetaste.net/m3u8?originId=XODM5MDAyNTM2&id=5620"
                     }
                 }
             ],
             "recommend": [
                 {
                 "Id": "1520",
                 "Author": "Pierre  DUCOS",
                 "Year": "2014",
                 "Duration": "08:31",
                 "VideoUrl": "http://v.youku.com/v_show/id_XODM3OTQ5ODQw.html",
                 "VideoSite": "youku",
                 "Brief": "这是一个关于战争的动画短片，看完后，感触很多。战争从来都是残酷的，绝不是糖果与烟花堆积出的和平有趣。短片的前半段很甜美可爱，后半段就灰暗阴沉了，一起来看看吧。",
                 "HomePic": "http://ww3.sinaimg.cn/large/0066P23Wjw1ern8suixa2j3034034aah.jpg",
                 "DetailPic": "http://ww2.sinaimg.cn/large/0066P23Wjw1ern8syrd90j30hs07s75l.jpg",
                 "Player": "http://i.animetaste.net/player/?id=5622",
                 "ShareLink": "http://aimozhen.com/view/5622/",
                 "Name": "La Detente(511)",
                 "VideoSource": {
                     "uhd": "http://i.animetaste.net/m3u8?originId=XODM3OTQ5ODQw&id=5622",
                     "hd": "http://i.animetaste.net/m3u8?originId=XODM3OTQ5ODQw&id=5622",
                     "sd": "http://i.animetaste.net/m3u8?originId=XODM3OTQ5ODQw&id=5622"
                     }
                 }
             ],
             "category": [
                 {
                 "id": "1",
                 "name": "归类无能",
                 "count": 0
                 }
             ]
         }
     }
 }
**/

    public VideoBean[] anime;
    public VideoBean[] recommend;
    public VideoCategoryBean[] category;

    protected VideoResponse(Parcel in) {
        anime = in.createTypedArray(VideoBean.CREATOR);
        recommend = in.createTypedArray(VideoBean.CREATOR);
        category = in.createTypedArray(VideoCategoryBean.CREATOR);
    }

    public static final Creator<VideoResponse> CREATOR = new Creator<VideoResponse>() {
        @Override
        public VideoResponse createFromParcel(Parcel in) {
            return new VideoResponse(in);
        }

        @Override
        public VideoResponse[] newArray(int size) {
            return new VideoResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(anime, flags);
        dest.writeTypedArray(recommend, flags);
        dest.writeTypedArray(category, flags);
    }
}
