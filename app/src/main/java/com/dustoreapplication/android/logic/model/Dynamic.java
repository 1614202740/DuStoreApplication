package com.dustoreapplication.android.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/12
 */
@Data
public class Dynamic implements Parcelable {
    /**
     * 帖子id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 标签id
     */
    private String tagId;

    /**
     * 帖子图片
     */
    private String imageUrl;

    protected Dynamic(Parcel in) {
        id = in.readString();
        userId = in.readString();
        title = in.readString();
        content = in.readString();
        byte tmpTop = in.readByte();
        top = tmpTop == 0 ? null : tmpTop == 1;
        tagId = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Dynamic> CREATOR = new Creator<Dynamic>() {
        @Override
        public Dynamic createFromParcel(Parcel in) {
            return new Dynamic(in);
        }

        @Override
        public Dynamic[] newArray(int size) {
            return new Dynamic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeByte((byte) (top == null ? 0 : top ? 1 : 2));
        dest.writeString(tagId);
        dest.writeString(imageUrl);
    }
}
