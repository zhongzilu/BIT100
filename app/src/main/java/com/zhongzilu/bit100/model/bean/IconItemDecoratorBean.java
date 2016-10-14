package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhongzilu on 16-9-17.
 */
public class IconItemDecoratorBean implements Parcelable{

    public int mIconRes;
    public String mTitle;

    public IconItemDecoratorBean(int mIconRes, String mTitle) {
        this.mIconRes = mIconRes;
        this.mTitle = mTitle;
    }

    protected IconItemDecoratorBean(Parcel in) {
        mIconRes = in.readInt();
        mTitle = in.readString();
    }

    public static final Creator<IconItemDecoratorBean> CREATOR = new Creator<IconItemDecoratorBean>() {
        @Override
        public IconItemDecoratorBean createFromParcel(Parcel in) {
            return new IconItemDecoratorBean(in);
        }

        @Override
        public IconItemDecoratorBean[] newArray(int size) {
            return new IconItemDecoratorBean[size];
        }
    };

    public int getmIconRes() {
        return mIconRes;
    }

    public void setmIconRes(int mIconRes) {
        this.mIconRes = mIconRes;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIconRes);
        dest.writeString(mTitle);
    }
}
