package com.dustoreapplication.android.ui.activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dustoreapplication.android.logic.model.bean.EvaluationInfo;
import com.dustoreapplication.android.logic.model.bean.Good;
import com.dustoreapplication.android.logic.model.vo.GoodSelectVo;

import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/6
 */
public class GoodDetailViewModel extends ViewModel {
    private final MutableLiveData<Good> goodInfo = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Good>> relatedGoods = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> descImages = new MutableLiveData<>();
    private final MutableLiveData<EvaluationInfo> evaluationInfo = new MutableLiveData<>();
    private final MutableLiveData<Integer> count = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> position = new MutableLiveData<>(0);
    private final MutableLiveData<Double> price = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<GoodSelectVo>> selectVos = new MutableLiveData<>();

    public void setGoodInfo(Good good){
        goodInfo.setValue(good);
        initSkuVo(good);
    }

    public MutableLiveData<Good> getGoodInfo() {
        return goodInfo;
    }

    public void setRelatedGoods(ArrayList<Good> goods){
        relatedGoods.setValue(goods);
    }
    public MutableLiveData<ArrayList<Good>> getRelatedGoods() {
        return relatedGoods;
    }

    public void setDescImages(ArrayList<String> descImages) {
        this.descImages.setValue(descImages);
    }
    public MutableLiveData<ArrayList<String>> getDescImages() {
        return descImages;
    }

    public void setEvaluationInfo(EvaluationInfo info){
        this.evaluationInfo.setValue(info);
    }

    public MutableLiveData<EvaluationInfo> getEvaluationInfo() {
        return evaluationInfo;
    }

    public MutableLiveData<Integer> getCount() {
        return count;
    }

    public void addCount(){
        if(count.getValue()!=null) {
            this.count.postValue(count.getValue() + 1);
        }
    }

    public void subCount(){
        if(count.getValue()!=null) {
            this.count.postValue(count.getValue() - 1);
        }
    }

    public void setSelectVo(ArrayList<GoodSelectVo> vos){
        this.selectVos.setValue(vos);
    }
    public MutableLiveData<ArrayList<GoodSelectVo>> getSelectVo() {
        return selectVos;
    }

    public MutableLiveData<Integer> getPosition() {
        return position;
    }

    public void setPosition(int position){
        this.position.setValue(position);
    }

    public void initSkuVo(Good good){
        String[] images = good.getImage();
        ArrayList<GoodSelectVo> selectResult = new ArrayList<>();
        for(int i=0; i<images.length; ++i){
            GoodSelectVo goodSelectVo = new GoodSelectVo();
            goodSelectVo.setPicture(images[i]);
            goodSelectVo.setTitle("种类"+i);
            goodSelectVo.setPrice(good.getPrice());
            goodSelectVo.setNum(good.getNum());
            selectResult.add(goodSelectVo);
        }
        setSelectVo(selectResult);
    }

    public MutableLiveData<Double> getPrice() {
        return price;
    }

    public void changeCount(int count){
        double price = count*selectVos.getValue().get(position.getValue()).getPrice();
        this.price.setValue(price);
    }

    public void changeGood(GoodSelectVo vo){
        double price = count.getValue()*vo.getPrice();
        this.price.setValue(price);
    }
}
