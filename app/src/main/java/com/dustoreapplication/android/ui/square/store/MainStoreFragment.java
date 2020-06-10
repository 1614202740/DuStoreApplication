package com.dustoreapplication.android.ui.square.store;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.Good;
import com.dustoreapplication.android.logic.model.PanelBlock;
import com.dustoreapplication.android.logic.receiver.GoodReceiver;
import com.dustoreapplication.android.logic.receiver.PanelBlockReceiver;
import com.dustoreapplication.android.logic.receiver.PanelSlideReceiver;
import com.dustoreapplication.android.logic.service.GoodIntentService;
import com.dustoreapplication.android.logic.service.PanelIntentService;
import com.dustoreapplication.android.view.SlideShowImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainStoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainStoreFragment extends Fragment {

    private List<View> blockViews = new ArrayList<>();
    private RecyclerView goodRecyclerView;
    private SlideShowImageView slideImageView;
    private AppCompatImageButton advertisingImageButton;

    private MainGoodAdapter mAdapter;
    private StoreViewModel mViewModel;

    public MainStoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainStoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainStoreFragment newInstance(String param1, String param2) {
        MainStoreFragment fragment = new MainStoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
//        final List<Bitmap> bitmaps = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_main_store, container, false);
        initView(view);
        mViewModel = new ViewModelProvider(requireActivity()).get(StoreViewModel.class);
        mViewModel.getPanelBlock().observe(getViewLifecycleOwner(),items->{
            for (int i=0; i<items.size(); ++i){
                View blockView = blockViews.get(i);
                Glide.with(blockView).load(Uri.parse(items.get(i).getCoverImage())).into((AppCompatImageView)blockView.findViewById(R.id.news_thumbnail_iv));
                ((AppCompatTextView)blockView.findViewById(R.id.news_title_tv)).setText(items.get(i).getName());
            }
        });
        mViewModel.getPanelSlide().observe(getViewLifecycleOwner(),slides -> {
            slideImageView.setData(slides);
        });
        mViewModel.getGoodInfo().observe(getViewLifecycleOwner(),goods -> {
            mAdapter.setGoods(goods);
        });
        if(mViewModel.getPanelSlide().getValue()==null){
            PanelIntentService.startActionSearchSlide(this.getContext());
        }
        if(mViewModel.getPanelBlock().getValue()==null) {
            PanelIntentService.startActionSearchBlock(this.getContext());
        }
        if(mViewModel.getGoodInfo().getValue()==null) {
            GoodIntentService.startActionSearchAll(this.getContext());
        }
        getActivity().registerReceiver(new PanelSlideReceiver(new PanelSlideReceiver.Message() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Intent intent) {
                mViewModel.setPanelSlide(intent.getParcelableArrayListExtra("panels"));
            }
        }),new IntentFilter(getString(R.string.panel_slide_receiver)));
        getActivity().registerReceiver(new PanelBlockReceiver(new PanelBlockReceiver.Message() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Intent intent) {
                mViewModel.setPanelBlock(intent.getParcelableArrayListExtra("blocks"));
            }
        }),new IntentFilter(getString(R.string.panel_block_receiver)));
        getActivity().registerReceiver(new GoodReceiver(new GoodReceiver.Message() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Intent intent) {
                mViewModel.setGoodInfo(intent.getParcelableArrayListExtra("goods"));
            }
        }),new IntentFilter(getString(R.string.good_all_receiver)));
        goodRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mAdapter = new MainGoodAdapter(mViewModel.getGoodInfo().getValue(),this.getContext());
        goodRecyclerView.setAdapter(mAdapter);
        Glide.with(this).load(R.mipmap.advertising_space).into(advertisingImageButton);
        return view;
    }

    private void initView(View root){
        TableRow rowOne = root.findViewById(R.id.main_store_news_row1);
        TableRow rowTwo = root.findViewById(R.id.main_store_news_row2);
        for(int i=0; i<rowOne.getChildCount(); ++i){
            blockViews.add(rowOne.getChildAt(i));
        }
        for(int i=0; i<rowTwo.getChildCount(); ++i){
            blockViews.add(rowTwo.getChildAt(i));
        }
        goodRecyclerView = root.findViewById(R.id.main_store_good_rv);
        slideImageView = root.findViewById(R.id.main_store_slide_iv);
        advertisingImageButton = root.findViewById(R.id.main_store_advertising_btn);
    }
}
