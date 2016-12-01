package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 视频清晰度资源实体类
 * Created by zhongzilu on 16-12-1.
 */
public class VideoSourceBean implements Parcelable{
/**
 "VideoSource": {
     "uhd": "http://i.animetaste.net/m3u8?originId=XODM3OTQ5ODQw&id=5622",
     "hd": "http://i.animetaste.net/m3u8?originId=XODM3OTQ5ODQw&id=5622",
     "sd": "http://i.animetaste.net/m3u8?originId=XODM3OTQ5ODQw&id=5622"
 }
 */
    public String uhd,
            hd,
            sd;

    protected VideoSourceBean(Parcel in) {
        uhd = in.readString();
        hd = in.readString();
        sd = in.readString();
    }

    public static final Creator<VideoSourceBean> CREATOR = new Creator<VideoSourceBean>() {
        @Override
        public VideoSourceBean createFromParcel(Parcel in) {
            return new VideoSourceBean(in);
        }

        @Override
        public VideoSourceBean[] newArray(int size) {
            return new VideoSourceBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uhd);
        dest.writeString(hd);
        dest.writeString(sd);
    }
}
