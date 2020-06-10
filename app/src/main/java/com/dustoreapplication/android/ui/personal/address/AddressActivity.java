package com.dustoreapplication.android.ui.personal.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.Address;
import com.dustoreapplication.android.logic.service.CustomerIntentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 16142
 */
public class AddressActivity extends AppCompatActivity {

    private RecyclerView infoRecyclerView;
    private List<Address> addresses = new ArrayList<>();
    private AddressAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                addresses.clear();
                for (Parcelable address: Objects.requireNonNull(intent.getParcelableArrayListExtra("addresses"))){
                    addresses.add((Address)address);
                }
                adapter.notifyDataSetChanged();
            }
        },new IntentFilter(getString(R.string.address_receiver)));
        CustomerIntentService.startActionAddress(this, DuApplication.customer.getId());
        infoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressAdapter(addresses);
        infoRecyclerView.setAdapter(adapter);
    }

    private void initView(){
        infoRecyclerView = findViewById(R.id.address_info_rv);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,AddressActivity.class);
        context.startActivity(intent);
    }
}
