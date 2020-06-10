package com.dustoreapplication.android.logic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 16142
 * on 2020/6/5
 * @author 16142
 */
public class PanelBlockReceiver extends BroadcastReceiver {

    private static final String ACTION_ERROR = "0";
    private static final String ACTION_SUCCESS = "1";
    private Message message;

    public PanelBlockReceiver(Message message) {
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
         * 查询面板按钮失败
         */
        void onError();

        /**
         * 查询面板按钮成功
         * @param intent 传入数据
         */
        void onSuccess(Intent intent);
    }
}
