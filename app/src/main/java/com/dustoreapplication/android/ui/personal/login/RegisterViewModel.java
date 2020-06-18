package com.dustoreapplication.android.ui.personal.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by 16142
 * on 2020/6/16
 */
public class RegisterViewModel extends ViewModel {
    private final MutableLiveData<Boolean> checkRegister = new MutableLiveData<>(false);
    private final MutableLiveData<String> phone = new MutableLiveData<>();

    public void setCheckRegister(boolean isCheck){
        this.checkRegister.setValue(isCheck);
    }

    public void setPhone(String phone){
        this.phone.setValue(phone);
    }

    public MutableLiveData<String> getPhone() {
        return phone;
    }

    public MutableLiveData<Boolean> getCheckRegister() {
        return checkRegister;
    }
}
