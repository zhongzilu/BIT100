package com.zhongzilu.bit100.model.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhongzilu on 16-12-4.
 */
public class LoginResponse implements Parcelable{
/**
    "status": "ok",
    "id": 1,
    "displayName": "钟子路",
    "loggedIn": true,
    "avatar": "http://gravatar.proxy.ustclug.org/avatar/fd87443b6a9ef7c30515480179fc86ae?s=96&d=retro&r=g",
    "homePage": 0,
    "blog_id": 1,
    "firstName": null,
    "lastName": null,
    "username": "zhongzilu",
    "contact": "zhongzilu520@163.com"

 */

    public String status,
        displayName,
        avatar,
        firstName,
        lastName,
        username,
        contact;
    public int id,
            homePage,
            blog_id;
    public boolean loggedIn;

    protected LoginResponse(Parcel in) {
        status = in.readString();
        displayName = in.readString();
        avatar = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        username = in.readString();
        contact = in.readString();
        id = in.readInt();
        homePage = in.readInt();
        blog_id = in.readInt();
        loggedIn = in.readByte() != 0;
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel in) {
            return new LoginResponse(in);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(displayName);
        dest.writeString(avatar);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(username);
        dest.writeString(contact);
        dest.writeInt(id);
        dest.writeInt(homePage);
        dest.writeInt(blog_id);
        dest.writeByte((byte) (loggedIn ? 1 : 0));
    }
}
