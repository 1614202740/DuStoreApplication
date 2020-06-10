package com.dustoreapplication.android.ui.personal.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by 16142
 * on 2020/5/16
 * @author 16142
 */
public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> phone = null;
    private MutableLiveData<String> code = null;
    private MutableLiveData<Boolean> hasPhone = null;
    private MutableLiveData<Boolean> hasCode = null;

    public MutableLiveData<String> getPhone() {
        return phone;
    }

    public MutableLiveData<String> getCode() {
        return code;
    }

    public void setPhone(String phone) {
        if(this.phone==null) {
            this.phone = new MutableLiveData<>();
        }
        this.phone.setValue(phone);
    }

    public void setCode(String code) {
        if(this.code==null) {
            this.code = new MutableLiveData<>();
        }
        this.code.setValue(code);
    }

    public MutableLiveData<Boolean> getHasPhone() {
        return hasPhone;
    }

    public void setHasPhone(Boolean hasPhone) {
        this.hasPhone.setValue(hasPhone);
    }

    public MutableLiveData<Boolean> getHasCode() {
        return hasCode;
    }

    public void setHasCode(Boolean hasCode) {
        this.hasCode.setValue(hasCode);
    }
}
