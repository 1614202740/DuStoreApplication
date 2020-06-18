package com.dustoreapplication.android.logic.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.bean.Address;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 16142
 * on 2020/6/12
 */
public class AddressIntentService extends IntentService {

    private static final String LOCAL_URL = DuApplication.LOCAL_HOST;
    private static final String ADDRESS_URL = "/address";

    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static final String ACTION_NEW = "com.dustoreapplication.android.logic.action.NEW";
    private static final String ACTION_DELETE = "com.dustoreapplication.android.logic.action.DELETE";
    private static final String ACTION_EDIT = "com.dustoreapplication.android.logic.action.EDIT";

    public AddressIntentService() {
        super("AddressIntentService");
    }

    public static void startActionNew(Context context, Address address){
        Intent intent = new Intent(context,AddressIntentService.class);
        intent.setAction(ACTION_NEW);
        intent.putExtra("address",address);
        context.startService(intent);
    }

    public static void startActionDelete(Context context, String addressId){
        Intent intent = new Intent(context,AddressIntentService.class);
        intent.setAction(ACTION_DELETE);
        intent.putExtra("addressId",addressId);
        context.startService(intent);
    }

    public static void startActionEdit(Context context, Address address){
        Intent intent = new Intent(context,AddressIntentService.class);
        intent.setAction(ACTION_EDIT);
        intent.putExtra("address",address);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent==null){
            return;
        }
        String action = intent.getAction();
        if(ACTION_NEW.equals(action)){
            handleActionNew(intent.getParcelableExtra("address"));
        }else if(ACTION_DELETE.equals(action)){
            handleActionDelete(intent.getStringExtra("addressId"));
        }
    }

    private void handleActionNew(Address address){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request= new Request.Builder()
                .header("token",DuApplication.token)
                .url(LOCAL_URL+ADDRESS_URL)
                .post(RequestBody.create(new Gson().toJson(address),JSON_TYPE))
                .build();
        System.out.println(new Gson().toJson(address));
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Intent intent = new Intent(getString(R.string.address_new_receiver));
                intent.putExtra("status","0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                Address address = null;
                try{
                    JSONObject items = new JSONObject(body).getJSONObject("data").getJSONObject("items");
                    address = new Gson().fromJson(items.toString(),Address.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(getString(R.string.address_new_receiver));
                intent.putExtra("status","1");
                intent.putExtra("address",address);
                sendBroadcast(intent);
            }
        });
    }

    private void handleActionDelete(String addressId){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .header("token",DuApplication.token)
                .url(LOCAL_URL+ADDRESS_URL+"/"+addressId)
                .delete()
                .build();
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
