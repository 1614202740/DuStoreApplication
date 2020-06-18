package com.dustoreapplication.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.bean.Address;
import com.dustoreapplication.android.logic.model.bean.Order;
import com.dustoreapplication.android.logic.receiver.AddressReceiver;
import com.dustoreapplication.android.logic.service.CustomerIntentService;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private AppCompatTextView consigneeTextView;
    private AppCompatTextView phoneTextView;
    private AppCompatTextView addressTextView;
    private RecyclerView itemsRecyclerView;
    private AppCompatTextView shippingTitleTextView;
    private AppCompatTextView shippingPriceTextView;
    private AppCompatTextView totalPriceTextView;
    private AppCompatImageView protocolImageView;
    private AppCompatButton checkButton;

    private OrderDetailViewModel mViewModel;
    private OrderDetailAdapter mAdapter;
    private Context context = this;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        mViewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);
        initView();
        initData();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v->finish());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewModel.getAddresses().observe(this,addresses -> {
            Integer position = mViewModel.getPositionAddress().getValue();
            mViewModel.setConsignee(addresses.get(position==null?0:position));
        });
        mViewModel.getPositionAddress().observe(this,position-> {
            ArrayList<Address> addresses = mViewModel.getAddresses().getValue();
            if(addresses!=null) {
                mViewModel.setConsignee(addresses.get(position));
            }
        });
        mViewModel.getConsignee().observe(this,consigneeVo -> {
            consigneeTextView.setText(consigneeVo.getName());
            phoneTextView.setText(consigneeVo.getPhone());
            addressTextView.setText(consigneeVo.getAddress());
        });

        mViewModel.getOrders().observe(this,orderItems -> {
            mViewModel.setTotalPrice(orderItems);
            if(mAdapter==null){
                mAdapter = new OrderDetailAdapter(orderItems);
                itemsRecyclerView.setAdapter(mAdapter);
            }else{
                mAdapter.setData(orderItems);
            }
        });
        mViewModel.getShipping().observe(this,shippingVo -> {
            shippingTitleTextView.setText(shippingVo.getTitle());
            shippingPriceTextView.setText(String.valueOf(shippingVo.getPrice()));
        });
        mViewModel.getTotalPrice().observe(this,price-> {
            totalPriceTextView.setText(String.valueOf(price));
            checkButton.setOnClickListener(v->{
                CheckStandActivity.startActivity(context,price,mViewModel.getOrderId().getValue());
                finish();
            });
        });
        mViewModel.getOrderId().observe(this,orderId-> {
            checkButton.setOnClickListener(v -> {
                CheckStandActivity.startActivity(context, mViewModel.getTotalPrice().getValue(), orderId);
                finish();
            });
        });
        Glide.with(this).load(R.mipmap.protocol_info).into(protocolImageView);
    }

    private void initView(){
        mToolbar = findViewById(R.id.order_detail_tb);
        consigneeTextView = findViewById(R.id.order_detail_consignee_tv);
        phoneTextView = findViewById(R.id.order_detail_phone_tv);
        addressTextView = findViewById(R.id.order_detail_address_tv);
        itemsRecyclerView = findViewById(R.id.order_detail_item_rv);
        shippingTitleTextView = findViewById(R.id.order_detail_shipping_title_tv);
        shippingPriceTextView = findViewById(R.id.order_detail_shipping_price_tv);
        totalPriceTextView = findViewById(R.id.order_detail_total_price_tv);
        protocolImageView = findViewById(R.id.order_detail_protocol_iv);
        checkButton = findViewById(R.id.order_detail_check_btn);
    }

    private void initData(){
        Order order = getIntent().getParcelableExtra("order");
        if(order!=null) {
            mViewModel.setShipping(order);
            mViewModel.setOrders(order.getItem());
            mViewModel.setOrderId(order.getOrderId());
        }
        CustomerIntentService.startActionAddress(this, DuApplication.customer.getId());
        registerReceiver(new AddressReceiver(new AddressReceiver.Message() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Intent intent) {
                mViewModel.setAddresses(intent.getParcelableArrayListExtra("addresses"));
            }
        }),new IntentFilter(getString(R.string.address_all_receiver)));
    }

    public static void startActivity(Context context, Order order){
        Intent intent = new Intent(context,OrderDetailActivity.class);
        intent.putExtra("order",order);
        context.startActivity(intent);
    }
}
