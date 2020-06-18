package com.dustoreapplication.android.logic.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.dustoreapplication.android.logic.service.CustomerIntentService;

/**
 * Created by 16142
 * on 2020/5/31
 */
public class CustomerManager {

    private static CustomerManager instance;

    private CustomerManager(){

    }

    public static CustomerManager getInstance() {
        if (instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }

    /**
     * 存储已登录的用户信息
     * @param context 上下文对象
     * @param username 用户名
     * @param password 密码
     */
    public void saveCustomerInfo(Context context, String username, String password){
        SharedPreferences sharedPreferences = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.apply();
    }

    /**
     * 获取存储的用户信息
     * @param context 上下文对象
     */
    public void getCustomerInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        CustomerIntentService.startActionLogin(context,
                "phone",
                sharedPreferences.getString("username",""),
                sharedPreferences.getString("password",""));
    }
}
