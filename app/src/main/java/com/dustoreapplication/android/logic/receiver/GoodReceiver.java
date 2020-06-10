package com.dustoreapplication.android.logic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 16142
 * on 2020/6/5
 */
public class GoodReceiver extends BroadcastReceiver {

    private static final String ACTION_ERROR = "0";
    private static final String ACTION_SUCCESS = "1";
    private Message message;

    public GoodReceiver(Message message){
        this.message = message;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = intent.getStringExtra("status");
        if(ACTION_ERROR.equals(status)){
            message.onError();
        }else if(ACTION_SUCCESS.equals(status)){
            message.onSuccess(intent);
        }
    }

    public interface Message{

        /**
         * 获取商品信息失败
         */
        void onError();

        /**
         * 获取商品信息成功
         * @param intent 输入数据
         */
        void onSuccess(Intent intent);
    }
}
