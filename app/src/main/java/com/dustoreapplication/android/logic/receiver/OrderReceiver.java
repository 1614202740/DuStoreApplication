package com.dustoreapplication.android.logic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 16142
 * on 2020/6/9
 */
public class OrderReceiver extends BroadcastReceiver {

    private static final String STATUS_ERROR  = "0";
    private static final String STATUS_SUCCESS  = "1";
    private Message message;

    public OrderReceiver(Message message){
        this.message = message;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = intent.getStringExtra("status");
        if(STATUS_ERROR.equals(status)){
            this.message.onError();
        }else if(STATUS_SUCCESS.equals(status)){
            this.message.onSuccess(intent);
        }
    }

    public interface Message{
        /**
         * 请求订单信息失败时
         */
        void onError();

        /**
         * 请求订单信息成功时
         * @param intent 输入数据
         */
        void onSuccess(Intent intent);
    }
}
