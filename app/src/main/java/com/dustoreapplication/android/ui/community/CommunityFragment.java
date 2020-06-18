package com.dustoreapplication.android.ui.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.ui.PageFragment;
import com.dustoreapplication.android.ui.community.recommend.RecommendFragment;
import com.dustoreapplication.android.ui.community.square.SquareFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 16142
 */
public class CommunityFragment extends Fragment {

    private CommunityViewModel communityViewModel;
    private TabLayout communityGuideTabLayout;
    private ViewPager2 communityGuideViewPager;

    private List<Fragment> pages = new ArrayList<>();

    {
        pages.add(RecommendFragment.newInstance());
        pages.add(SquareFragment.newInstance());
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        communityViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_community, container, false);
        initView(root);
        communityGuideViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return pages.get(position);
            }

            @Override
            public int getItemCount() {
                return pages.size();
            }
        });

        communityGuideTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                communityGuideViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        communityGuideViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                communityGuideTabLayout.setScrollPosition(position,0,false);
            }
        });

        new TabLayoutMediator(
                communityGuideTabLayout,
                communityGuideViewPager,
                (tab, position) -> {
                    switch (position){
                        case 0:{
                            tab.setText("推荐");
                            break;
                        }
                        case 1:{
                            tab.setText("广场");
                            break;
                        }
                        default:
                            break;
                    }
                }
        ).attach();
        return root;
    }

    private void initView(View root){
        communityGuideTabLayout = root.findViewById(R.id.community_guide_tl);
        communityGuideViewPager = root.findViewById(R.id.community_guide_vp2);
    }
}
