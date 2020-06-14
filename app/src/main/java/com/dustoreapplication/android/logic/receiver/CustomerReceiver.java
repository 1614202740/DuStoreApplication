package com.dustoreapplication.android.logic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author 16142
 */
public class CustomerReceiver extends BroadcastReceiver {

    private Message message;
    private final static String ERROR_LOGIN_CODE = "0";
    private final static String SUCCESS_LOGIN_CODE = "1";
    private final static String UNREGISTERED_CODE = "2";

    public CustomerReceiver(Message message){
        this.message = message;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = intent.getStringExtra("status");
        if(ERROR_LOGIN_CODE.equals(status)){
            message.onError();
        }
        else if(SUCCESS_LOGIN_CODE.equals(status)){
            message.onSuccess();
        }
        else if(UNREGISTERED_CODE.equals(status)){
            message.onUnregistered();
        }
    }

    public interface Message {

        /**
         * 当验证码验证失败
         */
        void onError();

        /**
         * 当验证码验证成功
         */
        void onSuccess();

        /**
         * 当前手机号还未注册
         */
        void onUnregistered();
    }
}
