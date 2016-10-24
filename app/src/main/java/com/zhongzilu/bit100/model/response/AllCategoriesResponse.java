package com.zhongzilu.bit100.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhongzilu.bit100.model.bean.CategoriesBean;

/**
 * 全部目录分类的实体类
 * Created by zhongzilu on 2016-10-21.
 */
public class AllCategoriesResponse implements Parcelable{
//    "status": "ok",
//    "count": 14,
//    "categories": [...]

    public String status,
            error;
    public int count;
    public CategoriesBean[] categories;


    protected AllCategoriesResponse(Parcel in) {
        status = in.readString();
        count = in.readInt();
        categories = in.createTypedArray(CategoriesBean.CREATOR);
    }

    public static final Creator<AllCategoriesResponse> CREATOR = new Creator<AllCategoriesResponse>() {
        @Override
        public AllCategoriesResponse createFromParcel(Parcel in) {
            return new AllCategoriesResponse(in);
        }

        @Override
        public AllCategoriesResponse[] newArray(int size) {
            return new AllCategoriesResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeInt(count);
        dest.writeTypedArray(categories, flags);
    }
}
