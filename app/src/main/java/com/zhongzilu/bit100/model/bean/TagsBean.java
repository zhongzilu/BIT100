package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 标签实例对象
 * Created by zhongzilu on 16-9-17.
 */
public class TagsBean implements Parcelable{

    public String[] mList;

    public List<String> mLists;

    public TagsBean(List<String> mLists) {
        this.mLists = mLists;
    }

    public TagsBean(String[] mList) {
        this.mList = mList;
    }

    protected TagsBean(Parcel in) {
        mList = in.createStringArray();
        mLists = in.createStringArrayList();
    }

    public static final Creator<TagsBean> CREATOR = new Creator<TagsBean>() {
        @Override
        public TagsBean createFromParcel(Parcel in) {
            return new TagsBean(in);
        }

        @Override
        public TagsBean[] newArray(int size) {
            return new TagsBean[size];
        }
    };

    public String[] getmList() {
        return mList;
    }

    public void setmList(String[] mList) {
        this.mList = mList;
    }

    public List<String> getmLists() {
        return mLists;
    }

    public void setmLists(List<String> mLists) {
        this.mLists = mLists;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(mList);
        dest.writeStringList(mLists);
    }
}
