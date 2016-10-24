package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhongzilu.bit100.application.util.Command;

/**
 * 文章详情Bean
 * Created by zhongzilu on 2016-10-20.
 */
public class ArticleDetailBean extends BaseBean implements Parcelable{

    public int id,
            comment_count;
    public String title,
            date,
            url,
            status,
            content,
            comment_status,
            thumbnail;

    public TagBean[] tags;
    public CategoriesBean[] categories;
    public AuthorBean author;
    public CustomFieldsBean custom_fields;
    public ThumbnailBean thumbnail_images;

    protected ArticleDetailBean(Parcel in) {
        id = in.readInt();
        comment_count = in.readInt();
        title = in.readString();
        date = in.readString();
        url = in.readString();
        status = in.readString();
        content = in.readString();
        comment_status = in.readString();
        thumbnail = in.readString();
        tags = in.createTypedArray(TagBean.CREATOR);
        categories = in.createTypedArray(CategoriesBean.CREATOR);
        author = in.readParcelable(AuthorBean.class.getClassLoader());
        custom_fields = in.readParcelable(CustomFieldsBean.class.getClassLoader());
    }

    public static final Creator<ArticleDetailBean> CREATOR = new Creator<ArticleDetailBean>() {
        @Override
        public ArticleDetailBean createFromParcel(Parcel in) {
            return new ArticleDetailBean(in);
        }

        @Override
        public ArticleDetailBean[] newArray(int size) {
            return new ArticleDetailBean[size];
        }
    };

    @Override
    public String getFilterCommand() {
        return Command.ArticleDetailCommand;
//        return Command.AllPostsCommand;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(comment_count);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(url);
        dest.writeString(status);
        dest.writeString(content);
        dest.writeString(comment_status);
        dest.writeString(thumbnail);
        dest.writeTypedArray(tags, flags);
        dest.writeTypedArray(categories, flags);
        dest.writeParcelable(author, flags);
        dest.writeParcelable(custom_fields, flags);
    }
}
