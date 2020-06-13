package com.dustoreapplication.android.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;
import lombok.NonNull;

/**
 * Created by 16142
 * on 2020/5/30
 * @author 16142
 */
@Data
public class Address implements Parcelable {
    @NonNull
    private String addressId;
    @NonNull
    private String userId;
    private String receiverName;
    private String phone;
    private String province;
    private String city;
    private String area;
    private String details;
    private Boolean isDefault = false;
    private Boolean isDeleted = false;

    public Address() {
    }

    public Address(String userId){
        this.userId = userId;
    }

    protected Address(Parcel in) {
        addressId = in.readString();
        userId = in.readString();
        receiverName = in.readString();
        phone = in.readString();
        province = in.readString();
        city = in.readString();
        area = in.readString();
        details = in.readString();
        byte tmpIsDefault = in.readByte();
        isDefault = tmpIsDefault == 0 ? null : tmpIsDefault == 1;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
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
        dest.writeString(receiverName);
        dest.writeString(phone);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(area);
        dest.writeString(details);
        dest.writeByte((byte) (isDefault == null ? 0 : isDefault ? 1 : 2));
    }
}
