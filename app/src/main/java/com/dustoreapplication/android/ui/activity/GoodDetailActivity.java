package com.dustoreapplication.android.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.EvaluationInfo;
import com.dustoreapplication.android.logic.model.Good;
import com.dustoreapplication.android.logic.model.dto.OrderDto;
import com.dustoreapplication.android.logic.model.vo.GoodSelectVo;
import com.dustoreapplication.android.logic.receiver.GoodReceiver;
import com.dustoreapplication.android.logic.receiver.OrderReceiver;
import com.dustoreapplication.android.logic.service.GoodIntentService;
import com.dustoreapplication.android.logic.service.OrderIntentService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class GoodDetailActivity extends AppCompatActivity {

    private Context context = this;
    private Toolbar mToolbar;
    private AppCompatImageView showImageView;
    private AppCompatTextView nameTextView;
    private AppCompatTextView priceTextView;
    private ViewPager2 relatedViewPager2;
    private LinearLayoutCompat infoLinearLayout;
    private RecyclerView targetRecyclerView;
    private AppCompatButton buyButton;



    private RelatedGoodAdapter relatedAdapter;
    private EvaluationTargetAdapter targetAdapter;
    private GoodSelectAdapter skuAdapter;
    private GoodDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        initView();
        initActionBar();
        Intent testData = initTestData();
        mViewModel = new ViewModelProvider(this).get(GoodDetailViewModel.class);
        mViewModel.getGoodInfo().observe(this,good->{
            Glide.with(this).load(good.getImage()[0]).into(showImageView);
            nameTextView.setText(good.getTitle());
            priceTextView.setText(String.valueOf(good.getPrice()));
            GoodIntentService.startActionSearchInfo(this,good.getId());
        });
        mViewModel.getDescImages().observe(this, this::initDesc);
        mViewModel.getRelatedGoods().observe(this,goods -> {
            if (relatedAdapter==null) {
                relatedAdapter = new RelatedGoodAdapter(goods,this);
                relatedViewPager2.setAdapter(relatedAdapter);
            }else{
                relatedAdapter.setData(goods);
            }
        });
        mViewModel.getEvaluationInfo().observe(this,info->{
            if(targetAdapter==null){
                targetAdapter = new EvaluationTargetAdapter(info.getTargetTitles(),info.getTargetCounts());
                targetRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                targetRecyclerView.setAdapter(targetAdapter);
            }else {
                targetAdapter.setData(info.getTargetTitles(),info.getTargetCounts());
            }
        });
        mViewModel.setGoodInfo(getIntent().getParcelableExtra("good"));
        mViewModel.setEvaluationInfo(testData.getParcelableExtra("evaluationInfo"));
        GoodIntentService.startActionSearchRelated(this);
        registerReceiver(new GoodReceiver(new GoodReceiver.Message() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Intent intent) {
                mViewModel.setRelatedGoods(intent.getParcelableArrayListExtra("relatedGoods"));
            }
        }),new IntentFilter(getString(R.string.good_related_receiver)));
        registerReceiver(new GoodReceiver(new GoodReceiver.Message() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Intent intent) {
                mViewModel.setDescImages(intent.getStringArrayListExtra("descImages"));
            }
        }),new IntentFilter(getString(R.string.good_info_receiver)));
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mViewModel.setPosition(intent.getIntExtra("position",0));
            }
        },new IntentFilter(getString(R.string.good_select_receiver)));
        registerReceiver(new OrderReceiver(new OrderReceiver.Message() {
            @Override
            public void onError() {
                Toast.makeText(context,"订单提交失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Intent intent) {
                OrderDetailActivity.startActivity(context, intent.getParcelableExtra("order"));
            }
        }),new IntentFilter(getString(R.string.order_new_receiver)));
        buyButton.setOnClickListener(v-> showBottomDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.good_detail_toolbar_menu, menu);
        return true;
    }

    private void initView(){
        mToolbar = findViewById(R.id.good_detail_tb);
        showImageView = findViewById(R.id.good_detail_show_iv);
        nameTextView = findViewById(R.id.good_detail_name_tv);
        priceTextView = findViewById(R.id.good_detail_price_tv);
        relatedViewPager2 = findViewById(R.id.good_detail_related_vp);
        infoLinearLayout = findViewById(R.id.good_detail_info_ll);
        targetRecyclerView = findViewById(R.id.good_detail_evaluation_target_rv);
        buyButton = findViewById(R.id.good_detail_buy_btn);
    }

    private void initActionBar(){
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 初始化描述长图
     * @param images 描述长图
     */
    private void initDesc(ArrayList<String> images){
        if(images==null){
            return;
        }
        for(int i=0; i<images.size(); ++i){
            AppCompatImageView imageView = new AppCompatImageView(this);
            LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
            Glide.with(this).load(images.get(i)).into(imageView);
            infoLinearLayout.addView(imageView,layoutParams);
        }
    }

    private Intent initTestData(){
        Intent intent = new Intent();
        EvaluationInfo info = new EvaluationInfo();
        info.setId("123213111");
        info.setCount(4348);
        info.setTargetTitles(new ArrayList<String>(){{
            add("好评");
            add("中评");
            add("差评");
        }});
        info.setTargetCounts(new ArrayList<Integer>(){{
            add(100);
            add(20);
            add(34);
        }});
        intent.putExtra("evaluationInfo",info);
        return intent;
    }

    public static void startActivity(Context context, Good good){
        Intent intent = new Intent(context,GoodDetailActivity.class);
        intent.putExtra("good",good);
        context.startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    private void showBottomDialog(){
        final Dialog dialog = new Dialog(this,R.style.FullScreenDialogStyle);
        View view  = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_buy,null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);

        Window window = dialog.getWindow();
        if(window!=null){
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }
        mViewModel.getPosition().observe(this,position->{
            ArrayList<GoodSelectVo> vos = mViewModel.getSelectVo().getValue();
            if(vos!=null) {
                GoodSelectVo good = vos.get(position);
                ((AppCompatTextView) view.findViewById(R.id.dialog_buy_title_tv)).setText("已选：" + good.getTitle());
                ((AppCompatTextView) view.findViewById(R.id.dialog_buy_price_tv)).setText(String.valueOf(good.getPrice()));
                ((AppCompatTextView) view.findViewById(R.id.dialog_buy_count_tv)).setText("库存" + good.getNum() + "件");
                Glide.with(this).load(good.getPicture()).into((AppCompatImageView) view.findViewById(R.id.dialog_buy_show_iv));
                mViewModel.changeGood(good);
            }
        });
        mViewModel.getCount().observe(this,count->{
            AppCompatImageButton addButton = view.findViewById(R.id.dialog_buy_count_add_btn);
            AppCompatImageButton subButton = view.findViewById(R.id.dialog_buy_count_sub_btn);
            addButton.setEnabled(count < mViewModel.getSelectVo().getValue().get(mViewModel.getPosition().getValue()).getNum());
            addButton.setOnClickListener(v-> mViewModel.addCount());
            subButton.setEnabled(count > 0);
            subButton.setOnClickListener(v-> mViewModel.subCount());
            ((AppCompatTextView)view.findViewById(R.id.dialog_buy_count_select_tv)).setText(String.valueOf(count));
            mViewModel.changeCount(count);
        });
        RecyclerView skuRecyclerView = view.findViewById(R.id.dialog_buy_sku_rv);
        mViewModel.getSelectVo().observe(this,goodSelectVos -> {
            if(skuAdapter==null){
                skuAdapter = new GoodSelectAdapter(goodSelectVos);
                skuRecyclerView.setAdapter(skuAdapter);
            }else{
                skuAdapter.setData(goodSelectVos);
            }
        });
        view.findViewById(R.id.dialog_buy_check_btn).setOnClickListener(v->{
            OrderDto orderDto = new OrderDto();
            orderDto.setUserId(DuApplication.customer.getId());
            orderDto.setItemMap(new HashMap<String, Integer>(){{
                put(mViewModel.getGoodInfo().getValue().getId(),mViewModel.getCount().getValue());
            }});
            double price = mViewModel.getPrice().getValue();
            orderDto.setPayment(price);
            orderDto.setPaymentType(0);
            orderDto.setPostFee(price <= 99 ? 0 : 6);
            orderDto.setStatus(0);
            orderDto.setAddressId("1266645794712920066");
            dialog.dismiss();
            OrderIntentService.startActionNew(this,orderDto);
        });
    }
}
