package com.dustoreapplication.android.logic.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.PanelBlock;
import com.dustoreapplication.android.logic.model.PanelSlide;

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
 * on 2020/6/4
 * @author 16142
 */
public class PanelIntentService extends IntentService {

    private static final String LOCAL_URL = DuApplication.LOCAL_HOST;
    private static final String PANEL_URL = "/panel";
    private static final String SLIDE_URL = "-content";
    private static final String ACTION_SEARCH_SLIDE = "com.dustoreapplication.android.logic.action.SLIDE";
    private static final String ACTION_SEARCH_BLOCK = "com.dustoreapplication.android.logic.action.BLOCK";
    public PanelIntentService() {
        super("PanelIntentService");
    }

    public static void startActionSearchSlide(Context context){
        Intent intent = new Intent(context,PanelIntentService.class);
        intent.setAction(ACTION_SEARCH_SLIDE);
        context.startService(intent);
    }

    public static void startActionSearchBlock(Context context){
        Intent intent = new Intent(context,PanelIntentService.class);
        intent.setAction(ACTION_SEARCH_BLOCK);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null) {
            final String action = intent.getAction();
            if(ACTION_SEARCH_SLIDE.equals(action)){
                handleSearchSlide();
            }else if(ACTION_SEARCH_BLOCK.equals(action)){
                handleSearchBlock();
            }
        }
    }

    private void handleSearchSlide(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(LOCAL_URL+PANEL_URL+SLIDE_URL+"/7").get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Intent intent = new Intent();
                intent.setAction(getString(R.string.panel_slide_receiver));
                intent.putExtra("status", "0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                final ArrayList<PanelSlide> result = new ArrayList<>();
                try {
                    JSONArray items = new JSONObject(body).getJSONObject("data").getJSONArray("items");
                    for (int i = 0; i < items.length(); ++i) {
                        JSONObject item = items.getJSONObject(i);
                        PanelSlide panelSlide = new PanelSlide();
                        panelSlide.setPicUrl(item.getString("picUrl"));
                        panelSlide.setPicUrl2(item.getString("picUrl2"));
                        panelSlide.setProductId(item.getInt("productId"));
                        result.add(panelSlide);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent();
                    intent.setAction(getString(R.string.panel_slide_receiver));
                    intent.putParcelableArrayListExtra("panels", result);
                    intent.putExtra("status", "1");
                    sendBroadcast(intent);
                }
            }
        });
    }

    private void handleSearchBlock(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(LOCAL_URL+PANEL_URL).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Intent intent = new Intent();
                intent.setAction(getString(R.string.panel_block_receiver));
                intent.putExtra("status","0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                ArrayList<PanelBlock> result = new ArrayList<>();
                try {
                    JSONArray items = new JSONObject(body).getJSONObject("data").getJSONArray("items");
                    for (int i=0; i<items.length(); ++i) {
                        JSONObject item = items.getJSONObject(i);
                        PanelBlock panelBlock = new PanelBlock();
                        panelBlock.setId(item.getInt("id"));
                        panelBlock.setName(item.getString("name"));
                        panelBlock.setCoverImage(item.getString("coverImage"));
                        result.add(panelBlock);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent();
                    intent.setAction(getString(R.string.panel_block_receiver));
                    intent.putParcelableArrayListExtra("blocks",result);
                    intent.putExtra("status","1");
                    sendBroadcast(intent);
                }
            }
        });
    }
}
