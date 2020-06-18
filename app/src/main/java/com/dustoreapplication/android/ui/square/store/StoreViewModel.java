package com.dustoreapplication.android.ui.square.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dustoreapplication.android.logic.model.bean.Good;
import com.dustoreapplication.android.logic.model.bean.PanelBlock;
import com.dustoreapplication.android.logic.model.bean.PanelSlide;

import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/5
 */
public class StoreViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<PanelSlide>> panelSlide = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<PanelBlock>> panelBlock = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Good>> goodInfo = new MutableLiveData<>();

    public void setPanelSlide(ArrayList<PanelSlide> slides){
        if(slides!=null){
            this.panelSlide.setValue(slides);
        }
    }

    public LiveData<ArrayList<PanelSlide>> getPanelSlide() {
        return panelSlide;
    }

    public void setPanelBlock(ArrayList<PanelBlock> blocks){
        panelBlock.setValue(blocks);
    }

    public LiveData<ArrayList<PanelBlock>> getPanelBlock() {
        return panelBlock;
    }

    public void setGoodInfo(ArrayList<Good> goodInfo){
        this.goodInfo.setValue(goodInfo);
    }

    public LiveData<ArrayList<Good>> getGoodInfo() {
        return goodInfo;
    }
}
