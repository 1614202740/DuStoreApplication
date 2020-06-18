package com.dustoreapplication.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.dustoreapplication.android.R;

public class AboutActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private AppCompatTextView versionTextView;
    private LinearLayoutCompat appLinearLayout;
    private LinearLayoutCompat memberLinearLayout;
    private LinearLayoutCompat ruleLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v->finish());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appLinearLayout.setOnClickListener(v->{

        });
        memberLinearLayout.setOnClickListener(v->{

        });
        ruleLinearLayout.setOnClickListener(v->{

        });
    }

    private void initView(){
        mToolbar = findViewById(R.id.toolbar);
        versionTextView = findViewById(R.id.about_version_tv);
        appLinearLayout = findViewById(R.id.about_app_info_ll);
        memberLinearLayout = findViewById(R.id.about_member_info_ll);
        ruleLinearLayout = findViewById(R.id.about_rule_info_ll);
    }
}
