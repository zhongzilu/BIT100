package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 标签模型bean，服务端返回的数据结构就是本类的数据结构
 * Created by zhongzilu on 2016-10-20.
 */
public class TagBean implements Parcelable{
//    "id": 6,
//    "slug": "android",
//    "title": "Android",
//    "description": "",
//    "post_count": 13

    public int id,
            post_count;

    public String slug,
            title,
            description;

    protected TagBean(Parcel in) {
        id = in.readInt();
        post_count = in.readInt();
        slug = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<TagBean> CREATOR = new Creator<TagBean>() {
        @Override
        public TagBean createFromParcel(Parcel in) {
            return new TagBean(in);
        }

        @Override
        public TagBean[] newArray(int size) {
            return new TagBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(post_count);
        dest.writeString(slug);
        dest.writeString(title);
        dest.writeString(description);
    }
}
