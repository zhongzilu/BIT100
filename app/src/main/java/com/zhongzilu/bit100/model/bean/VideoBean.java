package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 视频对象实体类
 * Created by zhongzilu on 16-12-1.
 */
public class VideoBean implements Parcelable{
/**
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
 **/

    public String Id,
        Author,
        Year,
        Duration,
        VideoUrl,
        VideoSite,
        Brief,
        HomePic,
        DetailPic,
        Player,
        ShareLink,
        Name;
    public VideoSourceBean VideoSource;

    public VideoBean(String id, String author, String year, String duration,
                     String videoUrl, String videoSite, String brief,
                     String homePic, String detailPic, String player,
                     String shareLink, String name, VideoSourceBean videoSource) {
        Id = id;
        Author = author;
        Year = year;
        Duration = duration;
        VideoUrl = videoUrl;
        VideoSite = videoSite;
        Brief = brief;
        HomePic = homePic;
        DetailPic = detailPic;
        Player = player;
        ShareLink = shareLink;
        Name = name;
        VideoSource = videoSource;
    }

    protected VideoBean(Parcel in) {
        Id = in.readString();
        Author = in.readString();
        Year = in.readString();
        Duration = in.readString();
        VideoUrl = in.readString();
        VideoSite = in.readString();
        Brief = in.readString();
        HomePic = in.readString();
        DetailPic = in.readString();
        Player = in.readString();
        ShareLink = in.readString();
        Name = in.readString();
        VideoSource = in.readParcelable(VideoSourceBean.class.getClassLoader());
    }

    public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel in) {
            return new VideoBean(in);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Author);
        dest.writeString(Year);
        dest.writeString(Duration);
        dest.writeString(VideoUrl);
        dest.writeString(VideoSite);
        dest.writeString(Brief);
        dest.writeString(HomePic);
        dest.writeString(DetailPic);
        dest.writeString(Player);
        dest.writeString(ShareLink);
        dest.writeString(Name);
        dest.writeParcelable(VideoSource, flags);
    }
}
