package com.dustoreapplication.android;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dustoreapplication.android.logic.model.Customer;
import com.dustoreapplication.android.logic.receiver.CustomerReceiver;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 16142
 * on 2020/5/13
 */
public class DuApplication extends Application {
    public static Context context;
    public static Stack<AppCompatActivity> activities = new Stack<>();
    public static Customer customer = null;
    public static String token = null;
    public final static String LOCAL_HOST = "http://192.168.1.109:9001";

    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(100),new MyThreadFactory(false));

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static void registerCustomerReceiver(Context context, CustomerReceiver.Message message){
        CustomerReceiver mReceiver = new CustomerReceiver(message);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.dustoreapplication.android.logic.CUSTOMER_RECEIVER");
        context.registerReceiver(mReceiver,intentFilter);
    }
}
