package com.dustoreapplication.android.ui.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.receiver.OrderReceiver;
import com.dustoreapplication.android.logic.service.OrderIntentService;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private OrderViewModel mViewModel;

    private Fragment[] fragments = new Fragment[]{
            new ListOrdersFragment(0),
            new ListOrdersFragment(1),
            new ListOrdersFragment(2),
            new ListOrdersFragment(3)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        if(mViewModel.getAllOrders().getValue()==null){
            OrderIntentService.startActionSearchAll(this, DuApplication.customer.getId());
        }
        registerReceiver(new OrderReceiver(new OrderReceiver.Message() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Intent intent) {
                mViewModel.setAllOrder(intent.getParcelableArrayListExtra("orders"));
            }
        }),new IntentFilter(getString(R.string.order_all_receiver)));
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments[position];
            }

            @Override
            public int getItemCount() {
                return fragments.length;
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.setScrollPosition(position,0,false);
            }
        });
        new TabLayoutMediator(
                tabLayout,
                viewPager,
                (tab, position) -> {
                    switch (position){
                        case 0:{
                            tab.setText("全部");
                            break;
                        }
                        case 1:{
                            tab.setText("待支付");
                            break;
                        }
                        case 2:{
                            tab.setText("待发货");
                            break;
                        }
                        case 3:{
                            tab.setText("待收货");
                            break;
                        }
                        default:
                            break;
                    }
                }
        ).attach();

    }

    private void initView(){
        toolbar = findViewById(R.id.order_toolbar);
        tabLayout = findViewById(R.id.order_status_tl);
        viewPager = findViewById(R.id.order_status_vp);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,OrderActivity.class);
        context.startActivity(intent);
    }
}
