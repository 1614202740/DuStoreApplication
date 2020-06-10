package com.dustoreapplication.android.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/5
 */
@Data
public class PanelBlock implements Parcelable {
    private int id;
    private String name;
    private String coverImage;

    public PanelBlock(){

    }
    protected PanelBlock(Parcel in) {
        id = in.readInt();
        name = in.readString();
        coverImage = in.readString();
    }

    public static final Creator<PanelBlock> CREATOR = new Creator<PanelBlock>() {
        @Override
        public PanelBlock createFromParcel(Parcel in) {
            return new PanelBlock(in);
        }

        @Override
        public PanelBlock[] newArray(int size) {
            return new PanelBlock[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(coverImage);
    }
}
