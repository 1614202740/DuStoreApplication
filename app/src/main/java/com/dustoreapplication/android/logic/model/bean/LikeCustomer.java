package com.dustoreapplication.android.logic.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/16
 */
@Data
public class LikeCustomer implements Parcelable {
    private String id;
    private String avatar;
    private String username;

    protected LikeCustomer(Parcel in) {
        id = in.readString();
        avatar = in.readString();
        username = in.readString();
    }

    public static final Creator<LikeCustomer> CREATOR = new Creator<LikeCustomer>() {
        @Override
        public LikeCustomer createFromParcel(Parcel in) {
            return new LikeCustomer(in);
        }

        @Override
        public LikeCustomer[] newArray(int size) {
            return new LikeCustomer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(avatar);
        dest.writeString(username);
    }
}
