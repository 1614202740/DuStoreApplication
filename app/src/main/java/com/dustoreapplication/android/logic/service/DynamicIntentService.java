package com.dustoreapplication.android.logic.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * Created by 16142
 * on 2020/6/12
 */
public class DynamicIntentService extends IntentService {

    private static final String ACTION_ALL = "com.dustoreapplication.android.logic.action.ALL";
    private static final String ACTION_DELETE = "com.dustoreapplication.android.logic.action.DELETE";
    private static final String ACTION_NEW = "com.dustoreapplication.android.logic.action.NEW";

    public DynamicIntentService() {
        super("DynamicIntentService");
    }

    public static void startActionSearchAll(){

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
