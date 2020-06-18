package com.dustoreapplication.android.ui.community.recommend;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.receiver.DynamicReceiver;
import com.dustoreapplication.android.logic.service.DynamicIntentService;
import com.dustoreapplication.android.ui.community.recommend.detail.RecommendDetailActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendFragment extends Fragment {

    private List<View> topics = new ArrayList<>();

    private RecyclerView cardRecyclerView;

    private RecommendCardAdapter mAdapter;
    private RecommendViewModel mViewModel;

    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        initTopic(view);
        initView(view);

        mViewModel = new ViewModelProvider(requireActivity()).get(RecommendViewModel.class);
        DynamicIntentService.startActionSearchAll(this.getContext());
        this.getContext().registerReceiver(new DynamicReceiver(new DynamicReceiver.Message() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Intent intent) {
                mViewModel.setDynamics(intent.getParcelableArrayListExtra("dynamics"));
            }
        }),new IntentFilter(getString(R.string.dynamic_all_receiver)));
        mViewModel.getDynamics().observe(getViewLifecycleOwner(),dynamics -> {
            if(mAdapter==null){
                mAdapter = new RecommendCardAdapter(dynamics);
                cardRecyclerView.setAdapter(mAdapter);
                mAdapter.setListener(position -> {
                    Intent intent = new Intent(getActivity(), RecommendDetailActivity.class);
                    intent.putExtra("dynamic",dynamics.get(position));
                    startActivity(intent);
                });
            }else {
                mAdapter.setDynamics(dynamics);
            }
        });
        cardRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        return view;
    }

    private void initTopic(View root){
        topics.add(root.findViewById(R.id.topic_1_ll));
        topics.add(root.findViewById(R.id.topic_2_ll));
        topics.add(root.findViewById(R.id.topic_3_ll));
        topics.add(root.findViewById(R.id.topic_4_ll));
        for(View topic:topics){
            Glide.with(getContext()).load(R.mipmap.topic_title_test).into((ImageView) topic.findViewById(R.id.recommend_topic_iv));
        }
    }

    private void initView(View root){
        cardRecyclerView = root.findViewById(R.id.recommend_card_rv);
    }
}
