package com.dustoreapplication.android.logic.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : 曹威
 * @date : 2020-06-08  11:04
 * @description :
 */
@Data
public class PostDto implements Serializable, Parcelable {

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
    private String[] tags;

    /**
     * 帖子图片
     */
    private String[] imageUrl;

    public PostDto(){

    }

    protected PostDto(Parcel in) {
        userId = in.readString();
        title = in.readString();
        content = in.readString();
        byte tmpTop = in.readByte();
        top = tmpTop == 0 ? null : tmpTop == 1;
        tags = in.createStringArray();
        imageUrl = in.createStringArray();
    }

    public static final Creator<PostDto> CREATOR = new Creator<PostDto>() {
        @Override
        public PostDto createFromParcel(Parcel in) {
            return new PostDto(in);
        }

        @Override
        public PostDto[] newArray(int size) {
            return new PostDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeByte((byte) (top == null ? 0 : top ? 1 : 2));
        dest.writeStringArray(tags);
        dest.writeStringArray(imageUrl);
    }
}
