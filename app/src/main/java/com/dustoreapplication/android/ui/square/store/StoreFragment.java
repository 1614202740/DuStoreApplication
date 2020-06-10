package com.dustoreapplication.android.ui.square.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.dustoreapplication.android.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class StoreFragment extends Fragment {

    private TabLayout kindTabLayout;
    private ViewPager2 storeViewPager;
    private int count = 10;

    private List<Fragment> pages = new ArrayList<>();

    {
        pages.add(new MainStoreFragment());
        for(int i=0; i<count; i++){
            pages.add(new OtherStoreFragment());
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_store, container, false);
        initView(root);
        storeViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return pages.get(position);
            }

            @Override
            public int getItemCount() {
                return count;
            }
        });
        kindTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                storeViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        storeViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                kindTabLayout.setScrollPosition(position, 0, false);
            }
        });

        new TabLayoutMediator(
                kindTabLayout,
                storeViewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("推荐");
                    } else {
                        tab.setText("分类");
                    }
                }
        ).attach();
        return root;
    }

    private void initView(View root){
        kindTabLayout = root.findViewById(R.id.store_nav_kind_tl);
        storeViewPager = root.findViewById(R.id.store_kind_vp2);
    }
}
