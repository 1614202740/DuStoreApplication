package com.dustoreapplication.android.logic.model.vo;

import lombok.Data;

/**
 * Created by 16142
 * on 2020/6/11
 */
@Data
public class PayTypeVo {
    private int logoId;
    private String title;

    public PayTypeVo(){

    }

    public PayTypeVo(int logoId, String title){
        this.logoId = logoId;
        this.title = title;
    }
}
