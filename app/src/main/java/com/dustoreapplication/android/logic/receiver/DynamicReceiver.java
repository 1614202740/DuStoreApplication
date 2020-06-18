package com.dustoreapplication.android.logic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 16142
 * on 2020/6/13
 */
public class DynamicReceiver extends BroadcastReceiver {
    private static final String STATUS_ERROR  = "0";
    private static final String STATUS_SUCCESS = "1";

    private Message message;

    public DynamicReceiver(Message message) {
        this.message = message;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = intent.getStringExtra("status");
        if(STATUS_ERROR.equals(status)){
            message.onError();
        }else if (STATUS_SUCCESS.equals(status)){
            message.onSuccess(intent);
        }
    }

    public interface Message{


        void onError();

        void onSuccess(Intent intent);
    }
}
