package com.dustoreapplication.android.ui.personal.address;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dustoreapplication.android.logic.model.bean.Address;

import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/12
 */
public class AddressViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Address>> addresses = new MutableLiveData<>();

    public void setAddressed(ArrayList<Address> addresses){
        this.addresses.setValue(addresses);
    }
    public MutableLiveData<ArrayList<Address>> getAddressed() {
        return addresses;
    }
}
