package com.dustoreapplication.android.logic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 16142
 * on 2020/6/11
 */
public class AddressReceiver extends BroadcastReceiver {

    private Message message;
    private static final String STATUS_ERROR  = "0";
    private static final String STATUS_SUCCESS  = "1";

    public AddressReceiver(Message message) {
        this.message = message;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String status = intent.getStringExtra("status");
        if(STATUS_ERROR.equals(status)){
            message.onError();
        }else if(STATUS_SUCCESS.equals(status)){
            message.onSuccess(intent);
        }
    }

    public interface Message{

        /**
         * 查询地址信息失败
         */
        void onError();

        /**
         * 查询地址信息成功
         * @param intent 输入数据
         */
        void onSuccess(Intent intent);
    }
}
