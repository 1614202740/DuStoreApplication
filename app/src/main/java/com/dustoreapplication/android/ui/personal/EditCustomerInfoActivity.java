package com.dustoreapplication.android.ui.personal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.MutableInt;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.service.CustomerIntentService;
import com.orhanobut.dialogplus.DialogPlus;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditCustomerInfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private LinearLayoutCompat avatarLinearLayout;
    private LinearLayoutCompat nameLinearLayout;
    private LinearLayoutCompat sexLinearLayout;
    private LinearLayoutCompat detailLinearLayout;
    private CircleImageView avatarImageView;
    private AppCompatTextView nameTextView;
    private AppCompatTextView sexTextView;
    private AppCompatTextView detailTextView;

    private EditInfoViewModel mViewModel;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer_info);
        initView();
        initToolbar();
        mViewModel = new ViewModelProvider(this).get(EditInfoViewModel.class);
        mViewModel.getAvatarUrl().observe(this,avatar-> Glide.with(this).load(avatar).into(avatarImageView));
        mViewModel.getName().observe(this,name->nameTextView.setText(name));
        mViewModel.getSex().observe(this,sex->sexTextView.setText(sex));
        mViewModel.getDetail().observe(this,detail->detailTextView.setText(detail));
        mViewModel.initData(DuApplication.customer);
        nameLinearLayout.setOnClickListener(v-> showNameDialog());
        sexLinearLayout.setOnClickListener(v->showSexDialog());
        detailLinearLayout.setOnClickListener(v->showDetailDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_info_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_save){
            CustomerIntentService.startActionEdit(this,mViewModel.customer);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar(){
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v->finish());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initView(){
        mToolbar = findViewById(R.id.toolbar);
        avatarLinearLayout = findViewById(R.id.edit_info_avatar_ll);
        nameLinearLayout = findViewById(R.id.edit_info_name_ll);
        sexLinearLayout = findViewById(R.id.edit_info_sex_ll);
        detailLinearLayout = findViewById(R.id.edit_info_detail_ll);
        avatarImageView = findViewById(R.id.edit_info_avatar_iv);
        nameTextView = findViewById(R.id.edit_info_name_tv);
        sexTextView = findViewById(R.id.edit_info_sex_tv);
        detailTextView = findViewById(R.id.edit_info_detail_tv);
    }
    /**
     * 显示姓名输入弹窗
     */
    private void showNameDialog(){
        final AppCompatEditText editText = new AppCompatEditText(this);
        builder = new AlertDialog.Builder(this).setTitle("输入昵称").setView(editText)
                .setPositiveButton("确认", (dialogInterface, i) -> {
                    Editable name = editText.getText();
                    if(name!=null){
                        mViewModel.setName(name.toString());
                        mViewModel.customer.setUsername(name.toString());
                    }
                });
        builder.create().show();
    }

    /**
     * 显示姓名单选弹窗
     */
    private void showSexDialog(){
        final String[] items = {"男", "女"};
        final int[] position = {0};
        builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("单选列表")
                .setSingleChoiceItems(items, 0, (dialogInterface, i) -> {
                    position[0] = i;
                })
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    if (position[0]>=0){
                        mViewModel.setSex(items[position[0]]);
                        mViewModel.customer.setSex(items[position[0]]);
                    }
                });
        builder.create().show();
    }

    /**
     * 显示签名输入弹窗
     */
    private void showDetailDialog(){
        final AppCompatEditText editText = new AppCompatEditText(this);
        builder = new AlertDialog.Builder(this).setTitle("输入签名").setView(editText)
                .setPositiveButton("确认", (dialogInterface, i) -> {
                    Editable detail = editText.getText();
                    if(detail!=null){
                        mViewModel.setDetail(detail.toString());
                    }
                });
        builder.create().show();
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,EditCustomerInfoActivity.class);
        context.startActivity(intent);
    }
}
