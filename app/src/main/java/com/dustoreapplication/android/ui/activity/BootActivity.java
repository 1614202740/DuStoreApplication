package com.dustoreapplication.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dustoreapplication.android.ComparableFutureTask;
import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.manager.CustomerManager;
import com.dustoreapplication.android.ui.MainActivity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 打开应用显示界面
 * @author 16142
 */
public class BootActivity extends AppCompatActivity {

    private AtomicInteger count = new AtomicInteger(3);

    private CustomerManager manager = CustomerManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DuApplication.activities.add(this);
        setContentView(R.layout.activity_boot);

        DuApplication.THREAD_POOL_EXECUTOR.execute(new ComparableFutureTask(1,() -> {
            while (count.get() > 0) {
                synchronized (Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait(1000);
                        count.decrementAndGet();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return 1;
        }));
        DuApplication.THREAD_POOL_EXECUTOR.execute(new ComparableFutureTask(1,()->{
            manager.getCustomerInfo(this);
            return 1;
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DuApplication.activities.remove(this);
    }

    public static void startActivity(Context context){
        context.startActivity(new Intent(context,BootActivity.class));
    }
}
