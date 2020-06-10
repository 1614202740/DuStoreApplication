package com.dustoreapplication.android.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/4
 */
@Data
public class PanelSlide implements Parcelable {
    private int productId;
    private String picUrl;
    private String picUrl2;


    public PanelSlide(){

    }

    protected PanelSlide(Parcel in) {
        productId = in.readInt();
        picUrl = in.readString();
        picUrl2 = in.readString();
    }

    public static final Creator<PanelSlide> CREATOR = new Creator<PanelSlide>() {
        @Override
        public PanelSlide createFromParcel(Parcel in) {
            return new PanelSlide(in);
        }

        @Override
        public PanelSlide[] newArray(int size) {
            return new PanelSlide[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(picUrl);
        dest.writeString(picUrl2);
    }
}
