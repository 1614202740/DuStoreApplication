package com.dustoreapplication.android.ui.personal.address;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.service.AddressIntentService;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

public class EditAddressActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatButton saveButton;
    private AppCompatEditText nameEditView;
    private AppCompatEditText phoneEditView;
    private TableRow infoTableRow;
    private AppCompatTextView infoTextView;
    private AppCompatEditText detailEditView;
    private CityPickerView mPicker = new CityPickerView();

    private EditAddressViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
        initActionbar();
        bindData();
        mPicker.init(this);
        mPicker.setConfig(new CityConfig.Builder()
                .confirmText("确定")
                .cancelText("取消")
                .build());
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                if(province!=null){
                    mViewModel.setProvince(province.getName());
                }
                if(city!=null){
                    mViewModel.setCity(city.getName());
                }
                if(district!=null){
                    mViewModel.setArea(district.getName());
                }
            }
        });
        infoTableRow.setOnClickListener(v-> mPicker.showCityPicker());
        nameEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.setName(s.toString());
            }
        });
        phoneEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.setPhone(s.toString());
            }
        });
        detailEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.setDetail(s.toString());
            }
        });
        saveButton.setOnClickListener(v-> {
            AddressIntentService.startActionNew(this,mViewModel.address);
            finish();
        });
    }

    private void initActionbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    private void bindData() {
        mViewModel = new ViewModelProvider(this).get(EditAddressViewModel.class);
        mViewModel.getProvince().observe(this, province -> {
            infoTextView.setText(province + mViewModel.getCity().getValue() + mViewModel.getArea().getValue());
            mViewModel.address.setProvince(province);
        });
        mViewModel.getCity().observe(this, city -> {
            infoTextView.setText(mViewModel.getProvince().getValue() + city + mViewModel.getArea().getValue());
            mViewModel.address.setCity(city);
        });
        mViewModel.getArea().observe(this, area -> {
            infoTextView.setText(mViewModel.getProvince().getValue() + mViewModel.getCity().getValue() + area);
            mViewModel.address.setArea(area);
        });
        mViewModel.getDetail().observe(this, detail -> {
            mViewModel.address.setDetails(detail);
        });
        mViewModel.getName().observe(this, name -> {
            mViewModel.address.setReceiverName(name);
        });
        mViewModel.getPhone().observe(this,phone->{
            mViewModel.address.setPhone(phone);
        });
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        saveButton = findViewById(R.id.add_address_save_btn);
        nameEditView = findViewById(R.id.add_address_name_ev);
        phoneEditView = findViewById(R.id.add_address_phone_ev);
        infoTableRow = findViewById(R.id.add_address_info_btn);
        infoTextView = findViewById(R.id.add_address_info_tv);
        detailEditView = findViewById(R.id.add_address_detail_ev);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, EditAddressActivity.class);
        context.startActivity(intent);
    }
}
