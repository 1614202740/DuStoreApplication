package com.dustoreapplication.android.ui.personal.space;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.ui.personal.EditCustomerInfoActivity;
import com.dustoreapplication.android.ui.personal.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpaceActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CircleImageView avatarImageView;
    private AppCompatButton editButton;
    private AppCompatTextView nameTextView;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;

    private List<Fragment> pages = new ArrayList<>();

    {
        pages.add(DynamicFragment.newInstance());
        pages.add(LikeFragment.newInstance());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);
        initView();
        initToolbar();
        bindTabAndViewPager();
        Glide.with(this).load(DuApplication.customer.getAvatar()).into(avatarImageView);
        nameTextView.setText(DuApplication.customer.getUsername());
        editButton.setOnClickListener(v-> EditCustomerInfoActivity.startActivity(this));
    }

    private void bindTabAndViewPager(){
        mViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return pages.get(position);
            }

            @Override
            public int getItemCount() {
                return pages==null?0:pages.size();
            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabLayout.setScrollPosition(position,0,false);
            }
        });
        new TabLayoutMediator(
                mTabLayout,
                mViewPager,
                (tab, position) -> {
                    switch (position){
                        case 0:{
                            tab.setText("动态");
                            break;
                        }
                        case 1:{
                            tab.setText("喜欢");
                            break;
                        }
                        default:
                            break;
                    }
                }
        ).attach();
    }

    private void initToolbar(){
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v->finish());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initView(){
        mToolbar = findViewById(R.id.toolbar);
        avatarImageView = findViewById(R.id.space_personal_avatar_iv);
        editButton = findViewById(R.id.space_personal_edit_btn);
        nameTextView = findViewById(R.id.space_personal_name_tv);
        mTabLayout = findViewById(R.id.space_personal_tl);
        mViewPager = findViewById(R.id.space_personal_vp);
    }

    public static void startActivity(Context context){
        if(DuApplication.token==null){
            context.startActivity(new Intent(context, LoginActivity.class));
        }else {
            context.startActivity(new Intent(context, SpaceActivity.class));
        }
    }
}
