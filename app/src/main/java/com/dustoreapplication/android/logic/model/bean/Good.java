package com.dustoreapplication.android.logic.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/2
 */
@Data
public class Good implements Parcelable {
    private String id;
    private String title;
    private String sellPoint;
    private float price;
    private int num;
    private int limitNum;
    private String[] image;
    private int cid;
    private int status;
    private String createTime;
    private String updateTime;

    protected Good(Parcel in) {
        id = in.readString();
        title = in.readString();
        sellPoint = in.readString();
        price = in.readFloat();
        num = in.readInt();
        limitNum = in.readInt();
        image = in.createStringArray();
        cid = in.readInt();
        status = in.readInt();
        createTime = in.readString();
        updateTime = in.readString();
    }

    public static final Creator<Good> CREATOR = new Creator<Good>() {
        @Override
        public Good createFromParcel(Parcel in) {
            return new Good(in);
        }

        @Override
        public Good[] newArray(int size) {
            return new Good[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(sellPoint);
        dest.writeFloat(price);
        dest.writeInt(num);
        dest.writeInt(limitNum);
        dest.writeStringArray(image);
        dest.writeInt(cid);
        dest.writeInt(status);
        dest.writeString(createTime);
        dest.writeString(updateTime);
    }
}
