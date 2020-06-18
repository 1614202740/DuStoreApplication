package com.dustoreapplication.android.ui.community.square;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dustoreapplication.android.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SquareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SquareFragment extends Fragment {

    private AppCompatTextView clubTitleTextView;
    private RecyclerView clubListRecyclerView;
    private RecyclerView contentRecycler;

    public SquareFragment() {
        // Required empty public constructor
    }

    public static SquareFragment newInstance() {
        SquareFragment fragment = new SquareFragment();
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
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        initView(view);

        clubTitleTextView.setText("我是天才小厨神");
        clubListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clubListRecyclerView.setAdapter(new ClubListAdapter());
        GridLayoutManager manager = new GridLayoutManager(getContext(),3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        contentRecycler.setLayoutManager(manager);
        contentRecycler.setAdapter(new ThumbnailListAdapter());
        return view;
    }

    private void initView(View root){
        clubTitleTextView = root.findViewById(R.id.recommend_square_club_title_tv);
        clubListRecyclerView = root.findViewById(R.id.recommend_square_club_list_rv);
        contentRecycler = root.findViewById(R.id.recommend_square_thumbnail_rv);
    }
}
