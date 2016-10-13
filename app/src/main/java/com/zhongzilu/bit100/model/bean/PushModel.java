package com.zhongzilu.bit100.model.bean;

/**
 * Created by zhongzilu on 2016-06-24.
 */
import android.os.Parcel;
import android.os.Parcelable;

public class PushModel implements Parcelable, Cloneable {
    /**接收到的推送消息的类型*/
    private int pushType;
    /**接收到的推送消息数据*/
    private Object pushObject;

    public PushModel(int pushType, Object list) {
        this.pushType = pushType;
        this.pushObject = list;
    }


    protected PushModel(Parcel in) {
        pushType = in.readInt();
    }

    public static final Creator<PushModel> CREATOR = new Creator<PushModel>() {
        @Override
        public PushModel createFromParcel(Parcel in) {
            return new PushModel(in);
        }

        @Override
        public PushModel[] newArray(int size) {
            return new PushModel[size];
        }
    };

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

    public Object getPushObject() {
        return pushObject;
    }

    public void setPushObject(Object pushObject) {
        this.pushObject = pushObject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pushType);
    }

    public PushModel clone(){
        PushModel o = null;
        try{
            o = (PushModel)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
    }
}

