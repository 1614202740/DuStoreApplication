package com.dustoreapplication.android.logic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 16142
 * on 2020/6/5
 */
public class PanelSlideReceiver extends BroadcastReceiver {

    private Message message;

    private final static String ACTION_ERROR = "0";
    private final static String ACTION_SUCCESS = "1";

    public PanelSlideReceiver(Message message){
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

    public void setMessage(Message message) {
        this.message = message;
    }

    public interface Message{
        /**
         * 获取轮播图失败
         */
        void onError();

        /**
         * 获取轮播图成功
         * @param intent 传入数据
         */
        void onSuccess(Intent intent);
    }
}
