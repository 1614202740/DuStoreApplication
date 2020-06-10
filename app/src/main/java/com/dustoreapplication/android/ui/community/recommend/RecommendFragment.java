package com.dustoreapplication.android.ui.community.recommend;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.ui.community.recommend.detail.RecommendDetailActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<View> topics = new ArrayList<>();

    private RecyclerView cardRecyclerView;

    public RecommendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendFragment newInstance(String param1, String param2) {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        initTopic(view);
        initView(view);

        RecommendCardAdapter adapter = new RecommendCardAdapter();
        adapter.setListener(position -> {
            Intent intent = new Intent(getActivity(), RecommendDetailActivity.class);
            intent.putExtra("publisher_name",adapter.username);
            startActivity(intent);
        });
        cardRecyclerView.setAdapter(adapter);
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
