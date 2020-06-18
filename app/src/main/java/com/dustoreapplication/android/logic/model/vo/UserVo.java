package com.dustoreapplication.android.logic.model.vo;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/13
 * @author 16142
 */
@Data
public class UserVo implements Parcelable {
    private String id;
    private String avatar;
    private String username;

    protected UserVo(Parcel in) {
        id = in.readString();
        avatar = in.readString();
        username = in.readString();
    }

    public static final Creator<UserVo> CREATOR = new Creator<UserVo>() {
        @Override
        public UserVo createFromParcel(Parcel in) {
            return new UserVo(in);
        }

        @Override
        public UserVo[] newArray(int size) {
            return new UserVo[size];
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
