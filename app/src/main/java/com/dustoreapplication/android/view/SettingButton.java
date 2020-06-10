package com.dustoreapplication.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.dustoreapplication.android.R;

/**
 * Created by 16142
 * on 2020/5/30
 * @author 16142
 */
public class SettingButton extends LinearLayoutCompat {

    private String showLabel;


    public SettingButton(Context context) {
        super(context);
    }

    public SettingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingButton);
        showLabel = typedArray.getString(R.styleable.SettingButton_settingShowLabel);
        typedArray.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.view_setting_button,this);
        AppCompatTextView labelTextView = view.findViewById(R.id.label_tv);

        labelTextView.setText(showLabel);
    }

    public SettingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
