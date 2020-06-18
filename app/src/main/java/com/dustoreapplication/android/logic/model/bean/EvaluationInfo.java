package com.dustoreapplication.android.logic.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/7
 */
@Data
public class EvaluationInfo implements Parcelable {
    private String id;
    private int count;
    private ArrayList targetTitles;
    private ArrayList targetCounts;

    public EvaluationInfo(){

    }

    protected EvaluationInfo(Parcel in) {
        id = in.readString();
        count = in.readInt();
        targetTitles = in.readArrayList(String.class.getClassLoader());
        targetCounts = in.readArrayList(Integer.class.getClassLoader());
    }

    public static final Creator<EvaluationInfo> CREATOR = new Creator<EvaluationInfo>() {
        @Override
        public EvaluationInfo createFromParcel(Parcel in) {
            return new EvaluationInfo(in);
        }

        @Override
        public EvaluationInfo[] newArray(int size) {
            return new EvaluationInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(count);
        dest.writeList(targetTitles);
        dest.writeList(targetCounts);
    }
}
