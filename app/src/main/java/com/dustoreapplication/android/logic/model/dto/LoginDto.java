package com.dustoreapplication.android.logic.model.dto;

import com.dustoreapplication.android.logic.model.Customer;
import com.google.gson.annotations.Expose;

/**
 * Created by 16142
 * on 2020/5/16
 * @author 16142
 */
public class LoginDto{
    @Expose
    private Customer data;

    public Customer getData() {
        return data;
    }

    public void setData(Customer data) {
        this.data = data;
    }
}
