package com.dustoreapplication.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.dto.PostDto;
import com.dustoreapplication.android.logic.service.DynamicIntentService;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.List;

public class SendDynamicsActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatImageView back;
    private AppCompatButton next;
    private TextInputEditText textInputEditText;
    private RecyclerView mRecyclerView;

    private List<ImageModel> imageModels;
    private PostDto post = new PostDto();
    private SendPhotoAdapter sendPhotoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dynamics);
        imageModels = (List<ImageModel>) getIntent().getSerializableExtra("selectedList");
        initWidgets();
    }

    private void initWidgets(){
        back = findViewById(R.id.send_dynamics_back);
        next = findViewById(R.id.send_dynamics_next);
        textInputEditText = findViewById(R.id.send_dynamics_textInputEditText);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.send_dynamics_recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        sendPhotoAdapter = new SendPhotoAdapter(imageModels);
        mRecyclerView.setAdapter(sendPhotoAdapter);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                post.setTitle(s.toString());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_dynamics_back:
                Intent intent = new Intent(SendDynamicsActivity.this,PhotoActivity.class);
                intent.putExtra("selectedList", (Serializable) imageModels);
                startActivity(intent);
                break;
            case R.id.send_dynamics_next:
                post.setUserId(DuApplication.customer.getId());
                String[] url = new String[imageModels.size()];
                for(int i=0; i<imageModels.size(); ++i){
                    url[i] = imageModels.get(i).getPath();
                }
                post.setImageUrl(url);
                DynamicIntentService.startActionNew(this,post);
            default:
                break;
        }
    }
}
