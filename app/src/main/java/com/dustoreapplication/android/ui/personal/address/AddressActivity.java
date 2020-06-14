package com.dustoreapplication.android.ui.personal.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.service.CustomerIntentService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * @author 16142
 */
public class AddressActivity extends AppCompatActivity {

    private RecyclerView infoRecyclerView;
    private FloatingActionButton floatingActionButton;

    private AddressAdapter mAdapter;
    private AddressViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();
        mViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        mViewModel.getAddressed().observe(this,addresses->{
            if(mAdapter==null){
                mAdapter = new AddressAdapter(this,addresses);
                infoRecyclerView.setAdapter(mAdapter);
            }else{
                mAdapter.setAddresses(addresses);
            }
        });
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mViewModel.setAddressed(intent.getParcelableArrayListExtra("addresses"));
            }
        },new IntentFilter(getString(R.string.address_all_receiver)));
        floatingActionButton.setOnClickListener(v->{
            EditAddressActivity.startActivity(this);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomerIntentService.startActionAddress(this, DuApplication.customer.getId());
    }

    private void initView(){
        infoRecyclerView = findViewById(R.id.address_info_rv);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,AddressActivity.class);
        context.startActivity(intent);
    }
}
