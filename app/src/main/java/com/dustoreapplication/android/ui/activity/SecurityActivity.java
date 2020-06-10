package com.dustoreapplication.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dustoreapplication.android.R;

public class SecurityActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        initView();
        this.setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView(){
        toolbar = findViewById(R.id.security_toolbar);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,SecurityActivity.class);
        context.startActivity(intent);
    }
}
