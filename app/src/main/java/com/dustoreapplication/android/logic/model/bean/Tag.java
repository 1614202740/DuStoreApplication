package com.dustoreapplication.android.logic.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 16142
 * on 2020/6/13
 */
public class Tag implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String image;
    private String postsCount;
    private String createTime;
    private String updateTime;

    protected Tag(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        image = in.readString();
        postsCount = in.readString();
        createTime = in.readString();
        updateTime = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(postsCount);
        dest.writeString(createTime);
        dest.writeString(updateTime);
    }
}
