package com.dustoreapplication.android.logic.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.manager.CustomerManager;
import com.dustoreapplication.android.logic.model.bean.Address;
import com.dustoreapplication.android.logic.model.bean.Customer;
import com.dustoreapplication.android.tool.ImageTool;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 * @author 16142
 * @since 2020/5/27
 */
public class CustomerIntentService extends IntentService {

    private static final String LOCAL_URL = DuApplication.LOCAL_HOST;
    private static final String USER_URL = "/user";
    private static final String CODE_URL = "/code";
    private static final String ADDRESS_URL = "/address";
    private static final String REGISTER_URL = "/register";

    private static final String ACTION_LOGIN = "com.dustoreapplication.android.logic.action.LOGIN";
    private static final String ACTION_CODE = "com.dustoreapplication.android.logic.action.CODE";
    private static final String ACTION_ADDRESS = "com.dustoreapplication.android.logic.action.ADDRESS";
    private static final String ACTION_REGISTER = "com.dustoreapplication.android.logic.action.REGISTER";
    private static final String ACTION_EDIT = "com.dustoreapplication.android.logic.action.EDIT";

    public CustomerIntentService() {
        super("CustomerIntentService");
    }


    /**
     * 启动登录任务
     * @param context 上下文对象
     * @param type 登录类型
     * @param key 登录键
     * @param value 登录值
     */
    public static void startActionLogin(Context context, String type, String key, String value){
        Intent intent = new Intent(context, CustomerIntentService.class);
        intent.setAction(ACTION_LOGIN);
        intent.putExtra("type", type);
        intent.putExtra("key", key);
        intent.putExtra("value", value);
        context.startService(intent);
    }

    public static void startActionCode(Context context, String phone){
        Intent intent = new Intent(context, CustomerIntentService.class);
        intent.setAction(ACTION_CODE);
        intent.putExtra("phone", phone);
        context.startService(intent);
    }

    public static void startActionAddress(Context context, String userId){
        Intent intent = new Intent(context, CustomerIntentService.class);
        intent.setAction(ACTION_ADDRESS);
        intent.putExtra("userId",userId);
        context.startService(intent);
    }

    public static void startActionRegister(Context context, String phone){
        Intent intent = new Intent(context, CustomerIntentService.class);
        intent.setAction(ACTION_REGISTER);
        intent.putExtra("phone",phone);
        context.startService(intent);
    }

    public static void startActionEdit(Context context, Customer customer){
        Intent intent = new Intent(context, CustomerIntentService.class);
        intent.setAction(ACTION_EDIT);
        intent.putExtra("customer",(Parcelable) customer);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(ACTION_LOGIN.equals(action)) {
                Bundle bundle = intent.getExtras();
                if(bundle!=null) {
                    handleActionLogin(bundle.getString("type"), bundle.getString("key"), bundle.getString("value"));
                }
            }
            else if(ACTION_CODE.equals(action)) {
                Bundle bundle = intent.getExtras();
                if(bundle!=null) {
                    handleActionCode(bundle.getString("phone"));
                }
            }
            else if(ACTION_ADDRESS.equals(action)) {
                Bundle bundle = intent.getExtras();
                if(bundle!=null){
                    handleActionAddress(bundle.getString("userId"));
                }
            }else if(ACTION_REGISTER.equals(action)){
                Bundle bundle = intent.getExtras();
                if(bundle!=null){
                    handleActionRegister(bundle.getString("phone"));
                }
            }else if(ACTION_EDIT.equals(action)){
                Bundle bundle = intent.getExtras();
                if(bundle!=null){
                    handleActionEdit(bundle.getParcelable("customer"));
                }
            }
        }
    }

    /**
     * 处理登录任务
     * @param type 登录类型
     * @param key 登录键
     * @param value 登录值
     */
    private void handleActionLogin(String type, String key, String value){
        String path = null;
        if(type!=null){
            switch (type){
                case "phone":{
                    path = CODE_URL;
                    break;
                }
                case "name":{
                    path = USER_URL;
                    break;
                }
                default:
                    Toast.makeText(DuApplication.context,"Do not have the assign type",Toast.LENGTH_SHORT).show();
            }
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(LOCAL_URL+path+"/"+key+"/"+value).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Intent intent = new Intent(getString(R.string.customer_receiver));
                intent.putExtra("status","0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = null;
                try {
                    String body = response.body().string();
                    JSONObject data = new JSONObject(body).getJSONObject("data");
                    result = data.getString("result");
                    Customer customer = new Gson().fromJson(data.getJSONObject("items").toString(), Customer.class);
                    String token = data.getString("token");
                    DuApplication.customer = customer;
                    DuApplication.token = token;
                    CustomerManager.getInstance().saveCustomerInfo(DuApplication.context,key,value);
                } catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(getString(R.string.customer_receiver));
                    intent.putExtra("status", result);
                    sendBroadcast(intent);
                }
            }
        });
    }

    /**
     * 向对应手机号的验证码
     * @param phone 手机号
     */
    private void handleActionCode(String phone){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(LOCAL_URL+CODE_URL+"/"+phone).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("Code","Error"+new Date());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String body = response.body().string();
                    String message = new JSONObject(body).getString("message");
                    Toast.makeText(DuApplication.context,message,Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 从服务器中获取用户的地址列表
     * @param userId 用户ID
     */
    private void handleActionAddress(String userId){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().header("token",DuApplication.token).url(LOCAL_URL+ADDRESS_URL+"/"+userId).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Intent intent = new Intent(getString(R.string.address_all_receiver));
                intent.putExtra("status","0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ArrayList<Address> addresses = new ArrayList<>();
                try {
                    String body = response.body().string();
                    JSONArray array = new JSONObject(body).getJSONObject("data").getJSONArray("items");
                    for(int i=0; i<array.length(); ++i){
                        addresses.add(new Gson().fromJson(array.get(i).toString(),Address.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(getString(R.string.address_all_receiver));
                    intent.putExtra("status","1");
                    intent.putParcelableArrayListExtra("addresses",addresses);
                    sendBroadcast(intent);
                }
            }
        });
    }

    /**
     * 处理注册服务
     * @param phone 注册号码
     */
    private void handleActionRegister(String phone){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(LOCAL_URL+REGISTER_URL)
                .post(RequestBody.create(new Gson().toJson(new Customer(phone)),mediaType))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Intent intent = new Intent(getString(R.string.customer_receiver));
                intent.putExtra("status","0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = null;
                try {
                    String body = response.body().string();
                    JSONObject data = new JSONObject(body).getJSONObject("data");
                    result = data.getString("result");
                    Customer customer = new Gson().fromJson(data.getJSONObject("items").toString(), Customer.class);
                    customer.setAvatarBitmap(ImageTool.getBitmapFromWeb(customer.getAvatar()));
                    String token = data.getString("token");
                    DuApplication.customer = customer;
                    DuApplication.token = token;
                } catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(getString(R.string.customer_receiver));
                    intent.putExtra("status", result);
                    sendBroadcast(intent);
                }
            }
        });
    }

    private void handleActionEdit(Customer customer){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(LOCAL_URL+USER_URL)
                .header("token",DuApplication.token)
                .put(RequestBody.create(new Gson().toJson(customer),mediaType))
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
                    JSONObject items = new JSONObject(body).getJSONObject("data").getJSONObject("items");
                    DuApplication.customer = new Gson().fromJson(items.toString(),Customer.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
