package com.dustoreapplication.android.logic.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/12
 * @author 16142
 */
@Data
public class AddressDto implements Serializable, Parcelable {

    private static final long serialVersionUID = 1618887603385009697L;

    private String userId;

    private String receiverName;

    private String phone;

    private String province;

    private String city;

    private String area;

    private String details;

    private Boolean isDefault;

    protected AddressDto(Parcel in) {
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

    public static final Creator<AddressDto> CREATOR = new Creator<AddressDto>() {
        @Override
        public AddressDto createFromParcel(Parcel in) {
            return new AddressDto(in);
        }

        @Override
        public AddressDto[] newArray(int size) {
            return new AddressDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
