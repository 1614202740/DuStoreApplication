package com.dustoreapplication.android.logic.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.bean.Good;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 16142
 * on 2020/6/2
 */
public class GoodIntentService extends IntentService {
    private static final String LOCAL_URL = DuApplication.LOCAL_HOST;
    private static final String GOOD_URL = "/item";

    private static final String ACTION_SEARCH_ALL = "com.dustoreapplication.android.logic.action.ALL";
    private static final String ACTION_SEARCH_PAGE = "com.dustoreapplication.android.logic.action.PAGE";
    private static final String ACTION_SEARCH_RELATED = "com.dustoreapplication.android.logic.action.RELATED";
    private static final String ACTION_SEARCH_INFO = "com.dustoreapplication.android.logic.action.INFO";

    public GoodIntentService() {
        super("GoodIntentService");
    }

    public static void startActionSearchAll(Context context){
        Intent intent = new Intent(context,GoodIntentService.class);
        intent.setAction(ACTION_SEARCH_ALL);
        context.startService(intent);
    }

    public static void startActionSearchRelated(Context context){
        Intent intent = new Intent(context,GoodIntentService.class);
        intent.setAction(ACTION_SEARCH_RELATED);
        context.startService(intent);
    }

    public static void startActionSearchInfo(Context context, String id){
        Intent intent = new Intent(context, GoodIntentService.class);
        intent.setAction(ACTION_SEARCH_INFO);
        intent.putExtra("id",id);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null){
            final String action = intent.getAction();
            if(ACTION_SEARCH_ALL.equals(action)){
                handleActionSearchAll();
            }else if(ACTION_SEARCH_RELATED.equals(action)){
                handleActionSearchRelated();
            }else if(ACTION_SEARCH_INFO.equals(action)){
                handleActionSearchInfo(intent.getStringExtra("id"));
            }
        }
    }

    private void handleActionSearchAll(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(LOCAL_URL+GOOD_URL+"/all").get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Intent intent = new Intent();
                intent.setAction(getResources().getString(R.string.good_all_receiver));
                intent.putExtra("status","0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ArrayList<Good> result = new ArrayList<>();
                try {
                    String body = response.body().string();
                    JSONArray items = new JSONObject(body).getJSONObject("data").getJSONArray("items");
                    for(int i=0; i<10; ++i){
                        result.add(new Gson().fromJson(items.getJSONObject(i).toString(),Good.class));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent();
                    intent.setAction(getResources().getString(R.string.good_all_receiver));
                    intent.putParcelableArrayListExtra("goods",result);
                    intent.putExtra("status","1");
                    sendBroadcast(intent);
                }
            }
        });
    }

    private void handleActionSearchRelated(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(LOCAL_URL+GOOD_URL+"/all").get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Intent intent = new Intent();
                intent.setAction(getResources().getString(R.string.good_related_receiver));
                intent.putExtra("status","0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ArrayList<Good> result = new ArrayList<>();
                try {
                    String body = response.body().string();
                    JSONArray items = new JSONObject(body).getJSONObject("data").getJSONArray("items");
                    for(int i=0; i<10; ++i){
                        result.add(new Gson().fromJson(items.getJSONObject(i).toString(),Good.class));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent();
                    intent.setAction(getResources().getString(R.string.good_related_receiver));
                    intent.putParcelableArrayListExtra("relatedGoods",result);
                    intent.putExtra("status","1");
                    sendBroadcast(intent);
                }
            }
        });
    }

    private void handleActionSearchInfo(String id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(LOCAL_URL+GOOD_URL+"/"+id).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Intent intent = new Intent();
                intent.setAction(getString(R.string.good_info_receiver));
                intent.putExtra("status","1");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                ArrayList<String> descImages = new ArrayList<>();
                try {
                    JSONArray itemDesc = new JSONObject(body).getJSONObject("data").getJSONObject("items").getJSONArray("itemDesc");
                    for(int i=0; i<itemDesc.length(); ++i){
                        descImages.add(itemDesc.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent();
                    intent.setAction(getString(R.string.good_info_receiver));
                    intent.putStringArrayListExtra("descImages",descImages);
                    intent.putExtra("status","1");
                    sendBroadcast(intent);
                }
            }
        });
    }
}
