package com.dustoreapplication.android.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/8
 */
@Data
public class OrderItem implements Parcelable {
    private String id;
    private String itemId;
    private String orderId;
    private int num;
    private String title;
    private double price;
    private double totalFee;
    private String picPath;

    protected OrderItem(Parcel in) {
        id = in.readString();
        itemId = in.readString();
        orderId = in.readString();
        num = in.readInt();
        title = in.readString();
        price = in.readDouble();
        totalFee = in.readDouble();
        picPath = in.readString();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(itemId);
        dest.writeString(orderId);
        dest.writeInt(num);
        dest.writeString(title);
        dest.writeDouble(price);
        dest.writeDouble(totalFee);
        dest.writeString(picPath);
    }
}
