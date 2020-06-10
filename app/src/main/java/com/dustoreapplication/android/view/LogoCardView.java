package com.dustoreapplication.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.dustoreapplication.android.R;

/**
 * Created by 16142
 * on 2020/6/4
 */
public class LogoCardView extends LinearLayoutCompat {

    private SquareImageView mImageView;
    private AppCompatTextView mTextView;

    private Drawable logo;
    private String title;

    public LogoCardView(Context context) {
        super(context);
    }

    public LogoCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context,attrs);
        initView(context);
        mImageView.setImageDrawable(logo);
        mTextView.setText(title);
    }

    private void initAttr(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LogoCardView);
        logo = typedArray.getDrawable(R.styleable.LogoCardView_logo);
        title = typedArray.getString(R.styleable.LogoCardView_title);
    }

    private void initView(Context context){
        View root = LayoutInflater.from(context).inflate(R.layout.view_logo_card,this);
        mImageView = root.findViewById(R.id.logo_card_iv);
        mTextView = root.findViewById(R.id.logo_card_tv);
    }
}
