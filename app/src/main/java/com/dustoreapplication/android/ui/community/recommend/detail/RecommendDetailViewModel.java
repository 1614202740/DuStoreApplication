package com.dustoreapplication.android.ui.community.recommend.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dustoreapplication.android.logic.model.bean.Dynamic;

/**
 * Created by 16142
 * on 2020/6/13
 * @author 16142
 */
public class RecommendDetailViewModel extends ViewModel {
    private final MutableLiveData<Dynamic> dynamic = new MutableLiveData<>();
    private final MutableLiveData<Integer> size = new MutableLiveData<>(0);

    public void setDynamic(Dynamic dynamic){
        this.dynamic.setValue(dynamic);
    }

    public MutableLiveData<Dynamic> getDynamic() {
        return dynamic;
    }

    public void setSize(int size){
        this.size.setValue(size);
    }

    public MutableLiveData<Integer> getSize() {
        return size;
    }
}
