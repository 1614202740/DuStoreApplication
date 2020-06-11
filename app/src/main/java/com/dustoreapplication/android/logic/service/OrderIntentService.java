package com.dustoreapplication.android.logic.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.Order;
import com.dustoreapplication.android.logic.model.OrderItem;
import com.dustoreapplication.android.logic.model.OrderShipping;
import com.dustoreapplication.android.logic.model.dto.OrderDto;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 16142
 * on 2020/6/8
 */
public class OrderIntentService extends IntentService {

    private static final String LOCAL_URL = DuApplication.LOCAL_HOST;
    private static final String ORDER_URL = "/order";

    private static final String ACTION_NEW = "com.dustoreapplication.android.logic.action.NEW";
    private static final String ACTION_SEARCH_INFO = "com.dustoreapplication.android.logic.action.INFO";
    private static final String ACTION_SEARCH_ALL= "com.dustoreapplication.android.logic.action.ALL";
    private static final String ACTION_SEARCH_PAGE = "com.dustoreapplication.android.logic.action.PAGE";
    private static final String ACTION_CANCEL = "com.dustoreapplication.android.logic.action.CANCEL";

    public OrderIntentService() {
        super("OrderIntentService");
    }

    public static void startActionNew(Context context, OrderDto order){
        Intent intent = new Intent(context,OrderIntentService.class);
        intent.setAction(ACTION_NEW);
        intent.putExtra("order", (Parcelable) order);
        context.startService(intent);
    }

    public static void startActionSearchAll(Context context, String userId){
        Intent intent = new Intent(context,OrderIntentService.class);
        intent.setAction(ACTION_SEARCH_ALL);
        intent.putExtra("userId",userId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent==null||DuApplication.token==null){
            return;
        }
        String action = intent.getAction();
        if(ACTION_NEW.equals(action)){
            handleActionNew(intent.getParcelableExtra("order"));
        }else if(ACTION_SEARCH_ALL.equals(action)){
            handleActionSearchAll(intent.getStringExtra("userId"));
        }
    }

    private void handleActionNew(OrderDto order){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        Request request = new Request.Builder()
                .header("token", DuApplication.token)
                .url(LOCAL_URL+ORDER_URL+"/new")
                .post(RequestBody.create(new Gson().toJson(order),mediaType))
                .build();
        System.out.println(new Gson().toJson(order));
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Intent intent = new Intent(getString(R.string.order_new_receiver));
                intent.putExtra("status","0");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                Order order = null;
                ArrayList<OrderItem> orderItems = new ArrayList<>();
                OrderShipping shipping = null;
                try{
                    JSONObject items = new JSONObject(body).getJSONObject("data").getJSONObject("items");
                    order = new Gson().fromJson(items.getJSONObject("order").toString(),Order.class);
                    JSONArray ordersArray = items.getJSONArray("orderItems");
                    for(int i=0; i<ordersArray.length(); ++i){
                        OrderItem orderItem = new Gson().fromJson(ordersArray.getJSONObject(i).toString(),OrderItem.class);
                        orderItems.add(orderItem);
                    }
                    shipping = new Gson().fromJson(items.getJSONObject("orderShipping").toString(),OrderShipping.class);
                    order.setItem(orderItems);
                    order.setShipping(shipping);
                }catch (JSONException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(getString(R.string.order_new_receiver));
                    intent.putExtra("status","1");
                    intent.putExtra("order",order);
                    sendBroadcast(intent);
                }
            }
        });
    }

    private void handleActionSearchInfo(){

    }

    private void handleActionSearchAll(String userId){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().header("token",DuApplication.token).url(LOCAL_URL+ORDER_URL+"/all/"+userId).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Intent intent = new Intent(getString(R.string.order_all_receiver));
                intent.putExtra("status","1");
                sendBroadcast(intent);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                ArrayList<Order> orders = new ArrayList<>();
                try {
                    JSONArray items = new JSONObject(body).getJSONObject("data").getJSONArray("items");
                    for (int i=0; i<items.length(); ++i){
                        JSONObject item = items.getJSONObject(i);
                        Order order = new Gson().fromJson(item.getJSONObject("order").toString(),Order.class);
                        JSONArray array =  item.getJSONArray("orderItems");
                        ArrayList<OrderItem> orderItems = new ArrayList<>();
                        for(int j=0; j<array.length(); ++j){
                            orderItems.add(new Gson().fromJson(array.getJSONObject(j).toString(),OrderItem.class));
                        }
                        OrderShipping shipping = new Gson().fromJson(item.getJSONObject("orderShipping").toString(),OrderShipping.class);
                        order.setItem(orderItems);
                        order.setShipping(shipping);
                        orders.add(order);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(getString(R.string.order_all_receiver));
                    intent.putParcelableArrayListExtra("orders",orders);
                    intent.putExtra("status","1");
                    sendBroadcast(intent);
                }
            }
        });
    }

    private void handleActionSearchPage(){

    }

    private void handleActionCancel(){

    }
}
