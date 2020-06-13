package com.dustoreapplication.android.logic.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.dustoreapplication.android.DuApplication;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 16142
 * on 2020/6/12
 */
public class DynamicIntentService extends IntentService {

    private static final String LOCAL_URL = DuApplication.LOCAL_HOST;
    private static final String DYNAMIC_URL = "/posts";

    private static final String ACTION_ALL = "com.dustoreapplication.android.logic.action.ALL";
    private static final String ACTION_DELETE = "com.dustoreapplication.android.logic.action.DELETE";
    private static final String ACTION_NEW = "com.dustoreapplication.android.logic.action.NEW";

    public DynamicIntentService() {
        super("DynamicIntentService");
    }

    public static void startActionSearchAll(Context context){
        Intent intent = new Intent(context,DynamicIntentService.class);
        intent.setAction(ACTION_ALL);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    private void handleActionSearchAll(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(LOCAL_URL+DYNAMIC_URL+"/all").get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }
}
