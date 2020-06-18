package com.dustoreapplication.android.ui.personal.address;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.logic.model.bean.Address;

/**
 * Created by 16142
 * on 2020/6/12
 */
public class EditAddressViewModel extends ViewModel {
    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final MutableLiveData<String> phone = new MutableLiveData<>();
    private final MutableLiveData<String> province = new MutableLiveData<>();
    private final MutableLiveData<String> city = new MutableLiveData<>();
    private final MutableLiveData<String> area = new MutableLiveData<>();
    private final MutableLiveData<String> detail = new MutableLiveData<>();
    public final Address address = new Address(DuApplication.customer.getId());

    public void setPhone(String phone){
        this.phone.setValue(phone);
    }

    public MutableLiveData<String> getPhone() {
        return phone;
    }

    public void setName(String name){
        this.name.setValue(name);
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setArea(String area){
        this.area.setValue(area);
    }

    public MutableLiveData<String> getArea() {
        return area;
    }

    public void setCity(String city){
        this.city.setValue(city);
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public void setProvince(String province){
        this.province.setValue(province);
    }

    public MutableLiveData<String> getProvince() {
        return province;
    }

    public void setDetail(String detail){
        this.detail.setValue(detail);
    }

    public MutableLiveData<String> getDetail() {
        return detail;
    }
}
