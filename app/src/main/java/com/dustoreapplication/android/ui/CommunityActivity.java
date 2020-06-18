package com.dustoreapplication.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.ui.PageFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 16142
 */
public class CommunityActivity extends AppCompatActivity {

    private ViewPager2 communityGuideVp;
    private TabLayout communityGuideTl;
    private List<Integer> colors = new ArrayList<>();
    {
        colors.add(android.R.color.black);
        colors.add(android.R.color.holo_purple);
        colors.add(android.R.color.holo_blue_dark);
        colors.add(android.R.color.holo_green_light);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        initView();
        communityGuideVp.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return PageFragment.newInstance();
            }

            @Override
            public int getItemCount() {
                return colors.size();
            }
        });
        communityGuideTl.addTab(communityGuideTl.newTab().setText("Tab0"));
        communityGuideTl.addTab(communityGuideTl.newTab().setText("Tab1"));
        communityGuideTl.addTab(communityGuideTl.newTab().setText("Tab2"));
        communityGuideTl.addTab(communityGuideTl.newTab().setText("Tab3"));

        communityGuideTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                communityGuideVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        communityGuideVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                communityGuideTl.setScrollPosition(position,0,false);
            }
        });
    }

    private void initView(){
        communityGuideVp = findViewById(R.id.community_guide_vp2);
        communityGuideTl = findViewById(R.id.community_guide_tl);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,CommunityActivity.class);
        context.startActivity(intent);
    }
}
