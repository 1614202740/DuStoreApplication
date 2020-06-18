package com.dustoreapplication.android.ui.personal;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.logic.model.bean.Customer;

/**
 * Created by 16142
 * on 2020/6/16
 */
public class EditInfoViewModel extends ViewModel {
    private final MutableLiveData<String> avatarUrl = new MutableLiveData<>();
    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final MutableLiveData<String> sex = new MutableLiveData<>();
    private final MutableLiveData<String> detail = new MutableLiveData<>();

    Customer customer = DuApplication.customer;

    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl.setValue(avatarUrl);
    }

    public void setName(String name){
        this.name.setValue(name);
    }

    public void setSex(String sex){
        this.sex.setValue(sex);
    }

    public void setDetail(String detail){
        this.detail.setValue(detail);
    }

    public MutableLiveData<String> getAvatarUrl() {
        return avatarUrl;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getSex() {
        return sex;
    }

    public MutableLiveData<String> getDetail() {
        return detail;
    }

    public void initData(Customer customer){
        if(customer==null){
            return;
        }
        avatarUrl.setValue(customer.getAvatar());
        name.setValue(customer.getUsername());
        sex.setValue(customer.getSex());
    }
}
