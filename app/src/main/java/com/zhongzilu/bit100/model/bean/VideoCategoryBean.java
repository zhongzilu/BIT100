package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhongzilu on 16-12-1.
 */
public class VideoCategoryBean implements Parcelable{
/**
 "id": "1",
 "name": "归类无能",
 "count": 0
 */

    public String id;
    public String name;
    public int count;

    protected VideoCategoryBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        count = in.readInt();
    }

    public static final Creator<VideoCategoryBean> CREATOR = new Creator<VideoCategoryBean>() {
        @Override
        public VideoCategoryBean createFromParcel(Parcel in) {
            return new VideoCategoryBean(in);
        }

        @Override
        public VideoCategoryBean[] newArray(int size) {
            return new VideoCategoryBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(count);
    }
}
