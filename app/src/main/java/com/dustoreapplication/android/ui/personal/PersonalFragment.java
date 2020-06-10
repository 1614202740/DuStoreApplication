package com.dustoreapplication.android.ui.personal;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.ui.activity.SettingActivity;
import com.dustoreapplication.android.ui.order.OrderActivity;
import com.dustoreapplication.android.ui.personal.login.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalFragment extends Fragment {

    private AppCompatImageView taskImageView;
    private AppCompatImageButton settingImageButton;
    private LinearLayoutCompat orderLinearLayout;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private void initView(View root){
        taskImageView = root.findViewById(R.id.personal_welfare_task_iv);
        settingImageButton = root.findViewById(R.id.personal_toolbar_setting_btn);
        orderLinearLayout = root.findViewById(R.id.personal_purchase_order_btn);
    }

    public PersonalFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        initView(view);
        Glide.with(view).load(R.mipmap.topic_title_test).into(taskImageView);
        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(DuApplication.customer!=null) {
            fragmentTransaction.replace(R.id.personal_info_fl, IsLoggedInFragment.newInstance());
            orderLinearLayout.setOnClickListener(v->OrderActivity.startActivity(this.getContext()));
        }else {
            fragmentTransaction.replace(R.id.personal_info_fl, NotLoggedInFragment.newInstance());
            orderLinearLayout.setOnClickListener(v->LoginActivity.startActivity(this.getContext()));
        }
        fragmentTransaction.commit();
        settingImageButton.setOnClickListener(v->startActivity(new Intent(this.getActivity(), SettingActivity.class)));
        return view;
    }
}
