package com.dustoreapplication.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.ui.personal.address.AddressActivity;
import com.dustoreapplication.android.view.SettingButton;

import java.util.Objects;

public class SettingActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private SettingButton addressButton;
    private SettingButton securityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v->finish());
        addressButton.setOnClickListener(v->startActivity(new Intent(this, AddressActivity.class)));
        securityButton.setOnClickListener(v->startActivity(new Intent(this,SecurityActivity.class)));
    }

    private void initView(){
        mToolbar = findViewById(R.id.setting_toolbar);
        addressButton = findViewById(R.id.setting_address_btn);
        securityButton = findViewById(R.id.setting_security_btn);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,SettingActivity.class);
        context.startActivity(intent);
    }
}
