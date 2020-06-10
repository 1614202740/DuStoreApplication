package com.dustoreapplication.android.ui.personal.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.receiver.CustomerReceiver;
import com.dustoreapplication.android.logic.service.CustomerIntentService;
import com.dustoreapplication.android.ui.MainActivity;

/**
 * @author 16142
 */
public class LoginActivity extends AppCompatActivity {

    private AppCompatImageButton cancelButton;
    private LinearLayoutCompat areaChangeButton;
    private AppCompatEditText phoneEditText;
    private AppCompatEditText codeEditText;
    private AppCompatButton sendCodeButton;
    private AppCompatButton determineButton;
    private AppCompatTextView errorTextView;

    private boolean hasPhone = false;
    private boolean hasCode = false;

    private LoginViewModel viewModel;

    private CustomerReceiver mReceiver;

    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        if(viewModel.getPhone()!=null&&viewModel.getCode()!=null) {
            viewModel.getPhone().observe(this, phone -> phoneEditText.setText(phone));
            viewModel.getCode().observe(this, code -> codeEditText.setText(code));
        }
        cancelButton.setOnClickListener(v -> startActivity(new Intent(this,MainActivity.class)));
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                errorTextView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    hasPhone = false;
                    resetDetermineButton();
                } else {
                    hasPhone = true;
                    viewModel.setPhone(s.toString());
                    if (hasPhone && hasCode) {
                        changeDetermineButton();
                    }
                }
            }
        });
        codeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                errorTextView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    hasCode = false;
                    resetDetermineButton();
                } else {
                    hasCode = true;
                    viewModel.setCode(s.toString());
                    if (hasPhone && hasCode) {
                        changeDetermineButton();
                    }
                }
            }
        });
        sendCodeButton.setOnClickListener(v-> CustomerIntentService.startActionCode(this,viewModel.getPhone().getValue()));
        determineButton.setOnClickListener(v-> CustomerIntentService.startActionLogin(this,"phone",viewModel.getPhone().getValue(),viewModel.getCode().getValue()));
        DuApplication.registerCustomerReceiver(this, new CustomerReceiver.Message() {
            @Override
            public void onError() {
                Toast.makeText(context,"验证码输入错误",Toast.LENGTH_SHORT).show();
                errorTextView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onSuccess() {
                startActivity(new Intent(context,MainActivity.class));
            }

            @Override
            public void onUnregistered() {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeDetermineButton(){
        determineButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, getTheme()));
        determineButton.setTextColor(getResources().getColor(android.R.color.white, getTheme()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void resetDetermineButton(){
        determineButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
        determineButton.setTextColor(getResources().getColor(R.color.colorAccent, getTheme()));
    }

    public void initView() {
        cancelButton = findViewById(R.id.login_cancel_btn);
        areaChangeButton = findViewById(R.id.login_change_area_btn);
        phoneEditText = findViewById(R.id.login_phone_ev);
        codeEditText = findViewById(R.id.login_code_ev);
        sendCodeButton = findViewById(R.id.login_resend_code_btn);
        determineButton = findViewById(R.id.login_determine_btn);
        errorTextView = findViewById(R.id.login_error_tv);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }
}
