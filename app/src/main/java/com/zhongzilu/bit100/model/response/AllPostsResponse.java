package com.zhongzilu.bit100.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhongzilu.bit100.model.bean.ArticleDetailBean;

/**
 * 获取全部文章的响应bean
 * Created by zhongzilu on 2016-10-21.
 */
public class AllPostsResponse implements Parcelable{

//    "status": "ok",
//    "count": 21,
//    "count_total": 21,
//    "pages": 0,
//    "posts": [...]

    public String status,
            error;
    public int count,
            count_total,
            pages;
    public ArticleDetailBean[] posts;

    protected AllPostsResponse(Parcel in) {
        status = in.readString();
        error = in.readString();
        count = in.readInt();
        count_total = in.readInt();
        pages = in.readInt();
        posts = in.createTypedArray(ArticleDetailBean.CREATOR);
    }

    public static final Creator<AllPostsResponse> CREATOR = new Creator<AllPostsResponse>() {
        @Override
        public AllPostsResponse createFromParcel(Parcel in) {
            return new AllPostsResponse(in);
        }

        @Override
        public AllPostsResponse[] newArray(int size) {
            return new AllPostsResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(error);
        dest.writeInt(count);
        dest.writeInt(count_total);
        dest.writeInt(pages);
        dest.writeTypedArray(posts, flags);
    }
}
