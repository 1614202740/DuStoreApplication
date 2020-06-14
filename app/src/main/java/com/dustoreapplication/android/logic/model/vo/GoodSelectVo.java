package com.dustoreapplication.android.logic.model.vo;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/10
 */
@Data
public class GoodSelectVo implements Parcelable {
    private String title;
    private double price;
    private int num;
    private String picture;

    public GoodSelectVo(){

    }
    protected GoodSelectVo(Parcel in) {
        title = in.readString();
        price = in.readDouble();
        num = in.readInt();
        picture = in.readString();
    }

    public static final Creator<GoodSelectVo> CREATOR = new Creator<GoodSelectVo>() {
        @Override
        public GoodSelectVo createFromParcel(Parcel in) {
            return new GoodSelectVo(in);
        }

        @Override
        public GoodSelectVo[] newArray(int size) {
            return new GoodSelectVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeDouble(price);
        dest.writeInt(num);
        dest.writeString(picture);
    }
}
