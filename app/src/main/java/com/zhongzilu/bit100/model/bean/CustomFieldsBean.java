package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 自定义字段的模型bean
 * Created by zhongzilu on 2016-10-20.
 */
public class CustomFieldsBean implements Parcelable{

    public String[] post_view_count;

    protected CustomFieldsBean(Parcel in) {
        post_view_count = in.createStringArray();
    }

    public static final Creator<CustomFieldsBean> CREATOR = new Creator<CustomFieldsBean>() {
        @Override
        public CustomFieldsBean createFromParcel(Parcel in) {
            return new CustomFieldsBean(in);
        }

        @Override
        public CustomFieldsBean[] newArray(int size) {
            return new CustomFieldsBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(post_view_count);
    }
}
