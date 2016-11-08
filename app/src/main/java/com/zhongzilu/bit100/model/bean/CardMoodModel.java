package com.zhongzilu.bit100.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhongzilu on 2016-11-07.
 */
public class CardMoodModel implements Parcelable{
    public String id;
    public String mood_date;
    public String mood_img;
    public String mood_text;
    public String type_name;

    public CardMoodModel(String id, String type_name, String mood_date, String mood_img, String mood_text) {
        this.id = id;
        this.mood_date = mood_date;
        this.mood_img = mood_img;
        this.mood_text = mood_text;
        this.type_name = type_name;
    }

    protected CardMoodModel(Parcel in) {
        id = in.readString();
        mood_date = in.readString();
        mood_img = in.readString();
        mood_text = in.readString();
        type_name = in.readString();
    }

    public static final Creator<CardMoodModel> CREATOR = new Creator<CardMoodModel>() {
        @Override
        public CardMoodModel createFromParcel(Parcel in) {
            return new CardMoodModel(in);
        }

        @Override
        public CardMoodModel[] newArray(int size) {
            return new CardMoodModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(mood_date);
        dest.writeString(mood_img);
        dest.writeString(mood_text);
        dest.writeString(type_name);
    }
}
