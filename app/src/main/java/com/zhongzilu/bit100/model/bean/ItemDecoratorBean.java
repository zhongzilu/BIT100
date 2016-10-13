package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhongzilu on 16-9-17.
 */
public class ItemDecoratorBean implements Parcelable{

    public String mTitle;
    public String mSubTitle;

    public ItemDecoratorBean(String mTitle, String mSubTitle) {
        this.mTitle = mTitle;
        this.mSubTitle = mSubTitle;
    }

    protected ItemDecoratorBean(Parcel in) {
        mTitle = in.readString();
        mSubTitle = in.readString();
    }

    public static final Creator<ItemDecoratorBean> CREATOR = new Creator<ItemDecoratorBean>() {
        @Override
        public ItemDecoratorBean createFromParcel(Parcel in) {
            return new ItemDecoratorBean(in);
        }

        @Override
        public ItemDecoratorBean[] newArray(int size) {
            return new ItemDecoratorBean[size];
        }
    };

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmSubTitle() {
        return mSubTitle;
    }

    public void setmSubTitle(String mSubTitle) {
        this.mSubTitle = mSubTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mSubTitle);
    }
}
