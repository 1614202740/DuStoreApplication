package com.dustoreapplication.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.Good;

/**
 * Created by 16142
 * on 2020/6/6
 * @author 16142
 */
public class GoodGeneralInfoView extends LinearLayoutCompat {

    private AppCompatImageView thumbnailImageView;
    private AppCompatTextView titleTextView;
    private AppCompatTextView priceTextView;

    private Good info;

    public void setInfo(Good info) {
        this.info = info;
    }

    public GoodGeneralInfoView(Context context) {
        super(context);
        initView(context);
    }

    public GoodGeneralInfoView(Context context, Good info) {
        super(context);
        this.info = info;
        initView(context);
    }

    public GoodGeneralInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.GoodGeneralInfoView);
        typedArray.recycle();
    }

    private void initView(Context context){
        View root = LayoutInflater.from(context).inflate(R.layout.view_good_general_info,this);
        thumbnailImageView = root.findViewById(R.id.good_general_iv);
        titleTextView = root.findViewById(R.id.good_general_title_tv);
        priceTextView = root.findViewById(R.id.good_general_price_tv);

        Glide.with(context).load(Uri.parse(info.getImage()[0])).into(thumbnailImageView);
        titleTextView.setText(info.getTitle());
        priceTextView.setText(String.valueOf(info.getPrice()));
    }
}
