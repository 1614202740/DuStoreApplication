package com.dustoreapplication.android.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.bean.Customer;
import com.dustoreapplication.android.logic.receiver.CustomerReceiver;
import com.dustoreapplication.android.ui.personal.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * @author 16142
 * @since 2020/5/27
 */
public class MainActivity extends AppCompatActivity {

    private final int LOGIN_CODE = 8080;
    private BottomNavigationView navView;
    private LinearLayoutCompat loginNotifyLinearLayout;
    private AppCompatImageButton loginCloseImageButton;
    private AppCompatButton loginGotoButton;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loginCloseImageButton.setOnClickListener(v-> loginNotifyLinearLayout.setVisibility(View.GONE));
        loginGotoButton.setOnClickListener(v->startActivityForResult(new Intent(this, LoginActivity.class),LOGIN_CODE));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_community, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        DuApplication.registerCustomerReceiver(this, new CustomerReceiver.Message() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess() {
                loginNotifyLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onUnregistered() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(DuApplication.customer!=null){
            loginNotifyLinearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==LOGIN_CODE){
            if(data!=null&&data.getExtras()!=null){
                DuApplication.customer = (Customer) data.getExtras().get("customer");
            }
        }
    }

    private void initView(){
        navView = findViewById(R.id.nav_view);
        loginNotifyLinearLayout = findViewById(R.id.main_login_notify_ll);
        loginCloseImageButton = findViewById(R.id.main_login_cancel_btn);
        loginGotoButton = findViewById(R.id.main_login_goto_btn);
    }
}
