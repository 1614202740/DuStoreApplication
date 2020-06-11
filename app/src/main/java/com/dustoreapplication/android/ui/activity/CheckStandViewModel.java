package com.dustoreapplication.android.ui.activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dustoreapplication.android.logic.model.vo.PayTypeVo;

import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/11
 * @author 16142
 */
public class CheckStandViewModel extends ViewModel {
    private final MutableLiveData<Double> totalPrice = new MutableLiveData<>();
    private final MutableLiveData<Integer> type = new MutableLiveData<>(0);
    private final MutableLiveData<ArrayList<PayTypeVo>> types = new MutableLiveData<>();

    public void setTotalPrice(double price){
        this.totalPrice.setValue(price);
    }

    public MutableLiveData<Double> getTotalPrice() {
        return totalPrice;
    }

    public void setType(int type){
        this.type.setValue(type);
    }

    public MutableLiveData<Integer> getType() {
        return type;
    }

    public void setTypes(ArrayList<PayTypeVo> types){
        this.types.setValue(types);
    }

    public MutableLiveData<ArrayList<PayTypeVo>> getTypes() {
        return types;
    }
}
