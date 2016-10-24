package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者模型bean
 * Created by zhongzilu on 2016-10-20.
 */
public class AuthorBean implements Parcelable{
//    "id": 1,
//    "slug": "zhongzilu",
//    "name": "zhongzilu",
//    "first_name": "",
//    "last_name": "",
//    "nickname": "zhongzilu",
//    "url": "",
//    "description": "i'm zilu zhong, i'm a Android Developer\r\n我是钟子路(zhongzilu)，是一个安卓开发者"

    public int id;
    public String slug,
            name,
            first_name,
            last_name,
            nickname,
            url,
            description;

    protected AuthorBean(Parcel in) {
        id = in.readInt();
        slug = in.readString();
        name = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        nickname = in.readString();
        url = in.readString();
        description = in.readString();
    }

    public static final Creator<AuthorBean> CREATOR = new Creator<AuthorBean>() {
        @Override
        public AuthorBean createFromParcel(Parcel in) {
            return new AuthorBean(in);
        }

        @Override
        public AuthorBean[] newArray(int size) {
            return new AuthorBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(slug);
        dest.writeString(name);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(nickname);
        dest.writeString(url);
        dest.writeString(description);
    }
}
