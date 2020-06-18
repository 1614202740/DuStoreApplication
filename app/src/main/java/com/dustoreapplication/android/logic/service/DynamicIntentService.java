package com.dustoreapplication.android.logic.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.bean.Dynamic;
import com.dustoreapplication.android.logic.model.dto.PostDto;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 16142
 * on 2020/6/12
 */
public class DynamicIntentService extends IntentService {

    private static final String LOCAL_URL = DuApplication.LOCAL_HOST;
    private static final String DYNAMIC_URL = "/posts";
    private static final String FILE_URL = "/file";

    private static final String ACTION_ALL = "com.dustoreapplication.android.logic.action.ALL";
    private static final String ACTION_DELETE = "com.dustoreapplication.android.logic.action.DELETE";
    private static final String ACTION_NEW = "com.dustoreapplication.android.logic.action.NEW";
    private static final String ACTION_POST_FILE = "com.dustoreapplication.android.logic.action.POST_FILE";

    public DynamicIntentService() {
        super("DynamicIntentService");
    }

    public static void startActionSearchAll(Context context){
        Intent intent = new Intent(context,DynamicIntentService.class);
        intent.setAction(ACTION_ALL);
        context.startService(intent);
    }

    public static void startActionNew(Context context, PostDto post){
        Intent intent = new Intent(context,DynamicIntentService.class);
        intent.setAction(ACTION_NEW);
        intent.putExtra("post",(Parcelable) post);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent==null){
            return;
        }
        String action = intent.getAction();
        if (ACTION_ALL.equals(action)){
            handleActionSearchAll();
        }else if(ACTION_NEW.equals(action)){
            handleActionNew(intent.getParcelableExtra("post"));
        }
    }

    private void handleActionSearchAll(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(LOCAL_URL+DYNAMIC_URL+"/all").get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Intent intent = new Intent(getString(R.string.dynamic_all_receiver));
                intent.putExtra("status","0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                ArrayList<Dynamic> dynamics = new ArrayList<>();
                try {
                    JSONArray items = new JSONObject(body).getJSONObject("data").getJSONArray("items");
                    for(int i=0; i<items.length(); ++i){
                        Dynamic dynamic = new Gson().fromJson(items.getJSONObject(i).toString(),Dynamic.class);
                        dynamics.add(dynamic);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getString(R.string.dynamic_all_receiver));
                intent.putExtra("status","1");
                intent.putParcelableArrayListExtra("dynamics",dynamics);
                sendBroadcast(intent);
            }
        });
    }

    private void handleActionNew(PostDto post){
        if (post==null){
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String[] imageUrl = post.getImageUrl();
        for(int i=0; i<imageUrl.length; ++i){
            File file = new File(imageUrl[i]);
            if(file.exists()){
                imageUrl[i] = fileUpload(file);
            }
        }
        post.setImageUrl(imageUrl);
        Request request = new Request.Builder()
                .url(LOCAL_URL+DYNAMIC_URL+"/new")
                .post(RequestBody.create(new Gson().toJson(post),mediaType))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Intent intent = new Intent(getString(R.string.dynamic_new_receiver));
                intent.putExtra("status","0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Intent intent = new Intent(getString(R.string.dynamic_new_receiver));
                intent.putExtra("status","1");
                sendBroadcast(intent);
            }
        });
    }

    private String fileUpload(File file){
        final String[] url = new String[]{null};
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody requestBody = RequestBody.create(file,mediaType);
        MultipartBody build = new MultipartBody.Builder()
                .addFormDataPart("file",file.getName(),requestBody)
                .setType(MultipartBody.FORM)
                .build();
        Request request = new Request.Builder()
                .url(LOCAL_URL+FILE_URL+"/upload")
                .post(build)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("ERROR");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                try {
                    JSONObject data = new JSONObject(body).getJSONObject("data");
                    url[0] = data.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return url[0];
    }
}
