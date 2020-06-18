package com.dustoreapplication.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.vo.PayTypeVo;
import com.dustoreapplication.android.logic.receiver.OrderReceiver;
import com.dustoreapplication.android.logic.service.OrderIntentService;

import java.util.ArrayList;

/**
 * @author 16142
 */
public class CheckStandActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private AppCompatTextView priceTextView;
    private RecyclerView typeRecyclerView;
    private LinearLayoutCompat submitLinearLayout;
    private AppCompatTextView typeTextView;
    private AppCompatTextView paymentTextView;

    private CheckStandViewModel mViewModel;
    private PayTypeAdapter mAdapter;

    private LifecycleOwner lifecycleOwner = this;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_stand);
        mViewModel = new ViewModelProvider(this).get(CheckStandViewModel.class);
        initView();
        initData();
        initTestData();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v->finish());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewModel.getTotalPrice().observe(this,price->{
            priceTextView.setText(String.valueOf(price));
            paymentTextView.setText(String.valueOf(price));
        });

        mViewModel.getTypes().observe(this,types->{
            if(mAdapter==null){
                mAdapter = new PayTypeAdapter(types,mViewModel,lifecycleOwner);
                typeRecyclerView.setAdapter(mAdapter);
            }else {
                mAdapter.setData(types);
            }
        });

        mViewModel.getType().observe(this,type->{
            ArrayList<PayTypeVo> vos = mViewModel.getTypes().getValue();
            if(vos!=null&&type<vos.size()) {
                typeTextView.setText(vos.get(type).getTitle()+"支付");
            }
        });
        submitLinearLayout.setOnClickListener(v->{
            finish();
        });

        registerReceiver(new OrderReceiver(new OrderReceiver.Message() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Intent intent) {
                finish();
            }
        }),new IntentFilter(getString(R.string.order_cancel_receiver)));
    }

    private void initTestData(){
        ArrayList<PayTypeVo> types = new ArrayList<>();
        types.add(new PayTypeVo(R.drawable.ic_pay_ali,"支付宝"));
        types.add(new PayTypeVo(R.drawable.ic_pay_wechat,"微信"));
        types.add(new PayTypeVo(R.drawable.ic_pay_paypal,"Paypel"));
        types.add(new PayTypeVo(R.drawable.ic_pay_union,"银联"));
        types.add(new PayTypeVo(R.drawable.ic_pay_quick_pass,"闪付"));
        mViewModel.setTypes(types);
    }
    private void initData(){
        double price = getIntent().getDoubleExtra("price",0.00);
        mViewModel.setTotalPrice(price);
        mViewModel.setOrderId(getIntent().getStringExtra("orderId"));
    }

    private void initView(){
        mToolbar = findViewById(R.id.check_stand_tb);
        priceTextView = findViewById(R.id.check_stand_price_tv);
        typeRecyclerView = findViewById(R.id.check_stand_pay_type_rv);
        submitLinearLayout = findViewById(R.id.check_stand_submit_ll);
        typeTextView = findViewById(R.id.check_stand_type_tv);
        paymentTextView = findViewById(R.id.check_stand_payment_tv);
        submitLinearLayout = findViewById(R.id.check_stand_submit_ll);
    }

    public static void startActivity(Context context, double price,String orderId){
        Intent intent = new Intent(context, CheckStandActivity.class);
        intent.putExtra("price",price);
        intent.putExtra("order",orderId);
        context.startActivity(intent);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        showCheckDialog();
    }

    private void showCheckDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_check_pay);
        dialog.show();

        mViewModel.getOrderId().observe(this,orderId->{
            dialog.findViewById(R.id.check_pay_cancel_btn).setOnClickListener(v->{
                OrderIntentService.startActionCancel(this,orderId);
            });
        });
        dialog.findViewById(R.id.check_pay_check_btn).setOnClickListener(v->{
            dialog.dismiss();
        });
    }
}
