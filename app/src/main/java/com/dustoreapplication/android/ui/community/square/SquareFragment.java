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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppCompatTextView clubTitleTextView;
    private RecyclerView clubListRecyclerView;
    private RecyclerView contentRecycler;

    public SquareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SquareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SquareFragment newInstance(String param1, String param2) {
        SquareFragment fragment = new SquareFragment();
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
