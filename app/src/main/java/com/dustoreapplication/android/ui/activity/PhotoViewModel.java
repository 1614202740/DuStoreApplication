package com.dustoreapplication.android.ui.activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class PhotoViewModel extends ViewModel {
    MutableLiveData<Integer> size = new MutableLiveData<>(0);

    public void addSize() {
        this.size.setValue(this.size.getValue()+1);
    }

    public void subSize() {
        this.size.setValue(this.size.getValue()-1);
    }

    public MutableLiveData<Integer> getSize(){
        return size;
    }
}
