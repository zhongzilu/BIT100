package com.zhongzilu.bit100.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhongzilu.bit100.model.bean.ArticleDetailBean;
import com.zhongzilu.bit100.model.bean.CategoriesBean;

/**
 * 根据目录查询文章列表数据响应Bean
 * Created by zhongzilu on 2016-10-27.
 */
public class AllPostsByCategoryResponse implements Parcelable{
//    "status": "ok",
//    "count": 2,
//    "pages": 0,
//    "category": {
//        "id": 33,
//        "slug": "bug-database",
//        "title": "Bug数据库",
//        "description": "",
//        "parent": 0,
//        "post_count": 2
//    },
//    "posts"：[...]

    public String status,
            error;
    public int count,
            pages;
    public CategoriesBean category;
    public ArticleDetailBean[] posts;

    protected AllPostsByCategoryResponse(Parcel in) {
        status = in.readString();
        error = in.readString();
        count = in.readInt();
        pages = in.readInt();
        category = in.readParcelable(CategoriesBean.class.getClassLoader());
        posts = in.createTypedArray(ArticleDetailBean.CREATOR);
    }

    public static final Creator<AllPostsByCategoryResponse> CREATOR = new Creator<AllPostsByCategoryResponse>() {
        @Override
        public AllPostsByCategoryResponse createFromParcel(Parcel in) {
            return new AllPostsByCategoryResponse(in);
        }

        @Override
        public AllPostsByCategoryResponse[] newArray(int size) {
            return new AllPostsByCategoryResponse[size];
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
        dest.writeInt(pages);
        dest.writeParcelable(category, flags);
        dest.writeTypedArray(posts, flags);
    }
}
