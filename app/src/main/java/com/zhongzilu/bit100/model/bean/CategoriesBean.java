package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 文章分类的Bean
 * Created by zhongzilu on 2016-10-20.
 */
public class CategoriesBean extends BaseBean implements Parcelable{
//    "id": 10,
//    "slug": "zhongzilu",
//    "title": "Home",
//    "description": "",
//    "parent": 0,
//    "post_count": 1

    public int id,
            parent;
    public String slug,
            title,
            post_count,
            description;

    protected CategoriesBean(Parcel in) {
        id = in.readInt();
        parent = in.readInt();
        post_count = in.readString();
        slug = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<CategoriesBean> CREATOR = new Creator<CategoriesBean>() {
        @Override
        public CategoriesBean createFromParcel(Parcel in) {
            return new CategoriesBean(in);
        }

        @Override
        public CategoriesBean[] newArray(int size) {
            return new CategoriesBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(parent);
        dest.writeString(post_count);
        dest.writeString(slug);
        dest.writeString(title);
        dest.writeString(description);
    }

    @Override
    public String getFilterCommand() {
        return null;
    }
}
