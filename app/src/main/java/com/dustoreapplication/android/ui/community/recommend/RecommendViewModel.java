package com.dustoreapplication.android.ui.community.recommend;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dustoreapplication.android.logic.model.bean.Dynamic;

import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/12
 */
public class RecommendViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Dynamic>> dynamics = new MutableLiveData<>();

    public void setDynamics(ArrayList<Dynamic> dynamics){
        this.dynamics.setValue(dynamics);
    }

    public MutableLiveData<ArrayList<Dynamic>> getDynamics() {
        return dynamics;
    }
}
