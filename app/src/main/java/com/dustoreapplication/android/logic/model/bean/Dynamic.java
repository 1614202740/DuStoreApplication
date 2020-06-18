package com.dustoreapplication.android.logic.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.dustoreapplication.android.logic.model.vo.UserVo;

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
    private UserVo userVo;

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
    private Tag[] tagList;

    /**
     * 帖子图片
     */
    private String[] imageUrl;

    private int likeCount;
    private LikeCustomer[] likeList;
    private int commentCount;
    private boolean like;
    private String createTime;
    private String updateTime;

    protected Dynamic(Parcel in) {
        id = in.readString();
        userVo = in.readParcelable(UserVo.class.getClassLoader());
        title = in.readString();
        content = in.readString();
        byte tmpTop = in.readByte();
        top = tmpTop == 0 ? null : tmpTop == 1;
        tagList = in.createTypedArray(Tag.CREATOR);
        imageUrl = in.createStringArray();
        likeCount = in.readInt();
        likeList = in.createTypedArray(LikeCustomer.CREATOR);
        commentCount = in.readInt();
        like = in.readByte() != 0;
        createTime = in.readString();
        updateTime = in.readString();
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
        dest.writeParcelable(userVo, flags);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeByte((byte) (top == null ? 0 : top ? 1 : 2));
        dest.writeTypedArray(tagList, flags);
        dest.writeStringArray(imageUrl);
        dest.writeInt(likeCount);
        dest.writeTypedArray(likeList, flags);
        dest.writeInt(commentCount);
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeString(createTime);
        dest.writeString(updateTime);
    }
}
