package com.dustoreapplication.android.logic.model.bean;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/8
 */
@Data
public class Order implements Parcelable {
    private String orderId;
    private String userId;
    private double payment;
    private int paymentType;
    private int postFree;
    private int status;
    private String createName;
    private String updateTime;
    private String paymentTime;
    private String consignTime;
    private String endTime;
    private String closeTime;
    private String shippingName;
    private String shippingId;
    private String buyerMessage;
    private boolean buyerComment;
    private ArrayList<OrderItem> item;
    private OrderShipping shipping;

    protected Order(Parcel in) {
        orderId = in.readString();
        userId = in.readString();
        payment = in.readDouble();
        paymentType = in.readInt();
        postFree = in.readInt();
        status = in.readInt();
        createName = in.readString();
        updateTime = in.readString();
        paymentTime = in.readString();
        consignTime = in.readString();
        endTime = in.readString();
        closeTime = in.readString();
        shippingName = in.readString();
        shippingId = in.readString();
        buyerMessage = in.readString();
        buyerComment = in.readByte() != 0;
        item = in.readArrayList(OrderItem.class.getClassLoader());
        shipping = in.readParcelable(OrderShipping.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(userId);
        dest.writeDouble(payment);
        dest.writeInt(paymentType);
        dest.writeInt(postFree);
        dest.writeInt(status);
        dest.writeString(createName);
        dest.writeString(updateTime);
        dest.writeString(paymentTime);
        dest.writeString(consignTime);
        dest.writeString(endTime);
        dest.writeString(closeTime);
        dest.writeString(shippingName);
        dest.writeString(shippingId);
        dest.writeString(buyerMessage);
        dest.writeByte((byte) (buyerComment ? 1 : 0));
        dest.writeList(item);
        dest.writeParcelable(shipping,OrderShipping.PARCELABLE_WRITE_RETURN_VALUE);
    }
}
