package com.dustoreapplication.android.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.tool.ContentResolverTool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PhotoActivity extends AppCompatActivity {
    private  static final int REQUEST_EXTERNAL_STORAGE=1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private List<ImageModel> imageModels;
    private List<ImageModel> selectImages;
    private Dialog dialog;

    private RecyclerView mRecyclerView;
    private PhotoAdapter photoAdapter;

    private PhotoViewModel mViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamics_photo);
        checkStoragePermissions(this);
        imageModels = ContentResolverTool.queryImagesFromExternal(this);


        selectImages = new ArrayList<>();
        initWidgets();

        mViewModel =new ViewModelProvider(this).get(PhotoViewModel.class);
        mViewModel.getSize().observe(this,size->{
            if(size == 0){
                dialog.dismiss();
            }else {
                showBottomDialog();
            }
        });

    }

    private void initWidgets(){
        mRecyclerView = findViewById(R.id.dynamics_photo_recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        photoAdapter = new PhotoAdapter(imageModels,selectImages,this);
        mRecyclerView.setAdapter(photoAdapter);
        dialog = new Dialog(this,R.style.FullScreenDialogStyle);
    }

    public static void checkStoragePermissions(Activity activity){
        try{
            //监测是否有写的权限
            int permission= ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            int permission1= ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_EXTERNAL_STORAGE");
            if(permission != PackageManager.PERMISSION_GRANTED){
                //没有写的权限，去申请写的权限，或弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
            if(permission1 != PackageManager.PERMISSION_GRANTED){
                //没有写的权限，去申请写的权限，或弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void showBottomDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.activity_dynamics_bottom,null);
        dialog.setContentView(view);
        RecyclerView bRecyclerView = dialog.findViewById(R.id.dynamics_bottom_recyclerView);
        bRecyclerView.setLayoutManager(new LinearLayoutManager(this){{
            setOrientation(LinearLayoutManager.HORIZONTAL);
        }});
        SelectedPhotoAdapter selectedPhotoAdapter = new SelectedPhotoAdapter(selectImages);
        bRecyclerView.setAdapter(selectedPhotoAdapter);
        dialog.setCanceledOnTouchOutside(false);

        AppCompatButton button = dialog.findViewById(R.id.dynamics_bottom_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoActivity.this,SendDynamics.class);
                intent.putExtra("selectedList", (Serializable) selectImages);
                startActivity(intent);
            }
        });

        Window window = dialog.getWindow();
        if(window != null){
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }
    }



}
