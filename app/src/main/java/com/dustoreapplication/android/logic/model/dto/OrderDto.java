package com.dustoreapplication.android.logic.model.dto;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 曹威
 * @date : 2020-06-04  20:34
 * @description :
 */
@Data
public class OrderDto implements Parcelable,Serializable {

    private static final long serialVersionUID = 3577165285981526007L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 商品Map: key商品id，value
     */
    private HashMap<String, Integer> itemMap;
    /**
     * 实付金额
     */
    private double payment;
    /**
     * 支付类型：1在线支付，2货到付款，0未支付
     */
    private int paymentType;
    /**
     * 邮费
     */
    private double postFee;
    /**
     * 订单状态：0未付款 1已付款 2未发货 3已发货 4交易成功 5交易关闭 6交易失败
     */
    private int status;
    /**
     * 地址id
     */
    private String addressId;

    public OrderDto(){

    }

    protected OrderDto(Parcel in) {
        addressId = in.readString();
        userId = in.readString();
        itemMap = in.readHashMap(null);
        payment = in.readDouble();
        paymentType = in.readInt();
        postFee = in.readDouble();
        status = in.readInt();
    }

    public static final Creator<OrderDto> CREATOR = new Creator<OrderDto>() {
        @Override
        public OrderDto createFromParcel(Parcel in) {
            return new OrderDto(in);
        }

        @Override
        public OrderDto[] newArray(int size) {
            return new OrderDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addressId);
        dest.writeString(userId);
        dest.writeMap(itemMap);
        dest.writeDouble(payment);
        dest.writeInt(paymentType);
        dest.writeDouble(postFee);
        dest.writeInt(status);
    }
}
