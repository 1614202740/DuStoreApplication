package com.dustoreapplication.android.logic.model.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/5/16
 * @author 16142
 */
@Data
public class Customer implements Serializable, Parcelable {
    @Expose
    private String id;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String phone;
    @Expose
    private String sex;
    @Expose
    private String createTime;
    @Expose
    private String updateTime;
    @Expose
    private String avatar;

    private Bitmap avatarBitmap;
    @Expose
    private Boolean isDeleted;

    public Customer(){

    }

    public Customer(String phone){
        this.phone = phone;
    }

    protected Customer(Parcel in) {
        id = in.readString();
        username = in.readString();
        password = in.readString();
        phone = in.readString();
        sex = in.readString();
        createTime = in.readString();
        updateTime = in.readString();
        avatar = in.readString();
        avatarBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        byte tmpIsDeleted = in.readByte();
        isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(phone);
        dest.writeString(sex);
        dest.writeString(createTime);
        dest.writeString(updateTime);
        dest.writeString(avatar);
        dest.writeParcelable(avatarBitmap, flags);
        dest.writeByte((byte) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
    }
}
