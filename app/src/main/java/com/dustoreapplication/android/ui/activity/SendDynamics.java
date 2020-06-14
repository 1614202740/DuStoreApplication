package com.dustoreapplication.android.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dustoreapplication.android.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SendDynamics extends AppCompatActivity implements View.OnClickListener {
    private AppCompatTextView back;
    private AppCompatButton next;
    private TextInputEditText textInputEditText;
    private RecyclerView mRecyclerView;

    private List<ImageModel> imageModels;
    private SendPhotoAdapter sendPhotoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dynamics);
        initWidgets();
        imageModels = (ArrayList) getIntent().getSerializableExtra("selectedList");

    }

    private void initWidgets(){
        back = findViewById(R.id.send_dynamics_back);
        next = findViewById(R.id.send_dynamics_next);
        textInputEditText = findViewById(R.id.send_dynamics_textInputEditText);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.dynamics_photo_recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        sendPhotoAdapter = new SendPhotoAdapter(imageModels);
        mRecyclerView.setAdapter(sendPhotoAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_dynamics_back:
                Intent intent = new Intent(SendDynamics.this,PhotoActivity.class);
                intent.putExtra("modifyList", (Serializable) imageModels);
                startActivity(intent);
                break;
            case R.id.send_dynamics_next:
                break;
            default:
                break;
        }
    }
}
