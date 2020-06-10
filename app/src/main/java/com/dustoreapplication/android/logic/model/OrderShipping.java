package com.dustoreapplication.android.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/8
 */
@Data
public class OrderShipping implements Parcelable {
    private String shippingId;
    private String orderId;
    private String addressId;
    private String userId;
    private String shippingInfo;

    protected OrderShipping(Parcel in) {
        shippingId = in.readString();
        orderId = in.readString();
        addressId = in.readString();
        userId = in.readString();
        shippingInfo = in.readString();
    }

    public static final Creator<OrderShipping> CREATOR = new Creator<OrderShipping>() {
        @Override
        public OrderShipping createFromParcel(Parcel in) {
            return new OrderShipping(in);
        }

        @Override
        public OrderShipping[] newArray(int size) {
            return new OrderShipping[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shippingId);
        dest.writeString(orderId);
        dest.writeString(addressId);
        dest.writeString(userId);
        dest.writeString(shippingInfo);
    }
}
