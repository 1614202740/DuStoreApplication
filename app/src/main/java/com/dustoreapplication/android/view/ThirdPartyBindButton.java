package com.dustoreapplication.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;

/**
 * Created by 16142
 * on 2020/6/1
 */
public class ThirdPartyBindButton extends LinearLayoutCompat {

    private String showTitle;
    private int uncheckedButtonColor;
    private int checkedButtonColor;
    private int uncheckedTextColor;
    private int checkedTextColor;
    private boolean isBinding;
    private Drawable icon;

    private AppCompatTextView titleTextView;
    private AppCompatButton bindButton;

    public ThirdPartyBindButton(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public ThirdPartyBindButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context,attrs);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initAttr(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThirdPartyBindButton);
        showTitle = typedArray.getString(R.styleable.ThirdPartyBindButton_thirdShowLabel);
        uncheckedButtonColor = typedArray.getColor(R.styleable.ThirdPartyBindButton_uncheckedButtonColor, getResources().getColor(R.color.colorPrimary, context.getTheme()));
        checkedButtonColor = typedArray.getColor(R.styleable.ThirdPartyBindButton_checkedButtonColor,getResources().getColor(R.color.colorPrimaryDark,context.getTheme()));
        uncheckedTextColor = typedArray.getColor(R.styleable.ThirdPartyBindButton_uncheckedTextColor, getResources().getColor(R.color.colorAccent, context.getTheme()));
        checkedTextColor = typedArray.getColor(R.styleable.ThirdPartyBindButton_checkedTextColor,getResources().getColor(android.R.color.white,context.getTheme()));
        isBinding = typedArray.getBoolean(R.styleable.ThirdPartyBindButton_isBinding,false);
        icon = typedArray.getDrawable(R.styleable.ThirdPartyBindButton_icon);

        typedArray.recycle();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_third_party_bind_button, this);

        titleTextView = view.findViewById(R.id.third_party_title_tv);
        titleTextView.setText(showTitle);
        view.findViewById(R.id.third_party_icon_iv).setBackground(icon);
        bindButton = view.findViewById(R.id.third_party_bind_btn);
        if(isBinding){
            bindButton.setTextColor(checkedTextColor);
            bindButton.setBackgroundColor(checkedButtonColor);
            bindButton.setText("解绑");
            isBinding = false;
        }else {
            bindButton.setTextColor(uncheckedTextColor);
            bindButton.setBackgroundColor(uncheckedButtonColor);
            bindButton.setText("绑定");
            isBinding = true;
        }

        bindButton.setOnClickListener(v->{
            AppCompatButton button = (AppCompatButton) v;
            if(isBinding){
                button.setTextColor(checkedTextColor);
                button.setBackgroundColor(checkedButtonColor);
                bindButton.setText("解绑");
                isBinding = false;
            }else {
                button.setTextColor(uncheckedTextColor);
                button.setBackgroundColor(uncheckedButtonColor);
                bindButton.setText("绑定");
                isBinding = true;
            }
        });
    }
}
