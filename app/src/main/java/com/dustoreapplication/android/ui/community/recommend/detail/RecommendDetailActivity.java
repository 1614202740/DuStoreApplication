package com.dustoreapplication.android.ui.community.recommend.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.vo.UserVo;
import com.dustoreapplication.android.tool.ScreenTool;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 16142
 */
public class RecommendDetailActivity extends AppCompatActivity {

    private AppCompatImageButton toolbarReturnButton;
    private CircleImageView publisherHeadCircleImageView;
    private AppCompatTextView publisherNameTextView;
    private AppCompatTextView publisherPositionTextView;
    private AppCompatButton subscribeButton;
    private AppCompatTextView containerTextView;
    private AppCompatTextView publishTimeTextView;
    private AppCompatImageButton goodButton;
    private AppCompatTextView goodCountTextView;
    private RecyclerView goodHeadRecyclerView;
    private AppCompatTextView commentCountTextView;
    private RecyclerView commentRecyclerView;
    private TabLayout thumbnailTabLayout;
    private ViewPager2 showViewPage;

    private RecommendDetailViewModel mViewModel;
    private GoodHeadListAdapter goodHeadListAdapter;
    private CommentListAdapter commentListAdapter;

    private void initView(){
        toolbarReturnButton = findViewById(R.id.recommend_detail_toolbar_return_btn);
        publisherHeadCircleImageView = findViewById(R.id.recommend_detail_publisher_head_iv);
        publisherNameTextView = findViewById(R.id.recommend_detail_publisher_name_tv);
        publisherPositionTextView = findViewById(R.id.recommend_detail_publisher_position_tv);
        subscribeButton = findViewById(R.id.recommend_detail_subscribe_btn);
//        showImageView = findViewById(R.id.recommend_detail_show_iv);
//        thumbnailRecyclerView = findViewById(R.id.recommend_detail_thumbnail_rv);
        containerTextView = findViewById(R.id.recommend_detail_contain_tv);
        publishTimeTextView = findViewById(R.id.recommend_detail_publish_time_tv);
        goodButton = findViewById(R.id.recommend_detail_good_btn);
        goodCountTextView = findViewById(R.id.recommend_detail_good_count_tv);
        goodHeadRecyclerView = findViewById(R.id.recommend_detail_good_rv);
        commentCountTextView = findViewById(R.id.recommend_detail_comment_count_tv);
        commentRecyclerView = findViewById(R.id.recommend_detail_comment_rv);
        thumbnailTabLayout = findViewById(R.id.recommend_detail_thumbnail_tl);
        showViewPage = findViewById(R.id.recommend_detail_show_vp);
    }

    public RecommendDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_detail);
        initView();
        toolbarReturnButton.setOnClickListener(v->finish());
        mViewModel = new ViewModelProvider(this).get(RecommendDetailViewModel.class);
        mViewModel.setDynamic(getIntent().getParcelableExtra("dynamic"));
        mViewModel.getDynamic().observe(this,dynamic -> {
            UserVo userVo = dynamic.getUserVo();
            Glide.with(getBaseContext()).load(userVo.getAvatar()).into(publisherHeadCircleImageView);
            mViewModel.setSize(dynamic.getCommentCount()==0?new Random().nextInt(5):dynamic.getCommentCount());
            publisherNameTextView.setText(userVo.getUsername());
            publisherPositionTextView.setText("江西省南昌市");
            containerTextView.setText(dynamic.getTitle());
            goodCountTextView.setText(String.valueOf(dynamic.getLikeCount()));
            publishTimeTextView.setText(dynamic.getUpdateTime());
            if(goodHeadListAdapter==null){
                goodHeadListAdapter = new GoodHeadListAdapter(dynamic.getLikeList());
                goodHeadRecyclerView.setAdapter(goodHeadListAdapter);
            }else {
                goodHeadListAdapter.setResources(dynamic.getLikeList());
            }
            showViewPage.setAdapter(new RecyclerView.Adapter() {

                String[] resources = dynamic.getImageUrl();

                class ViewHolder extends RecyclerView.ViewHolder {
                    public ViewHolder(@NonNull View itemView) {
                        super(itemView);
                    }
                }

                @NonNull
                @Override
                public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    AppCompatImageView view = new AppCompatImageView(parent.getContext());
                    view.setMaxHeight(ScreenTool.px2dip(parent.getContext(),512));
                    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return new ViewHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                    Glide.with(holder.itemView).load(resources[position]).into((AppCompatImageView)holder.itemView);
                }


                @Override
                public int getItemCount() {
                    return resources.length;
                }
            });

            new TabLayoutMediator(thumbnailTabLayout, showViewPage, (tab, position) -> {
                View view = LayoutInflater.from(this).inflate(R.layout.imageview_detail_thumbnail,tab.parent,false);
                AppCompatImageView imageView = view.findViewById(R.id.thumbnail_iv);
                Glide.with(view).load(dynamic.getImageUrl()[position]).into(imageView);
                tab.setCustomView(view);
            }).attach();
        });
        mViewModel.getSize().observe(this,size->{
            commentCountTextView.setText(String.valueOf(size));
            if(commentListAdapter==null){
                commentListAdapter = new CommentListAdapter(size);
                commentRecyclerView.setAdapter(commentListAdapter);
            }else {
                commentListAdapter.setSize(size);
            }
        });
//        LinearLayoutManager thumbnailLayoutManager = new LinearLayoutManager(this);
//        thumbnailLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        thumbnailRecyclerView.setLayoutManager(thumbnailLayoutManager);
//        thumbnailRecyclerView.setAdapter(new ThumbnailListAdapter(resources,showImageView));
        goodButton.setOnClickListener(v->{

        });


        showViewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                thumbnailTabLayout.setScrollPosition(position,0 ,false);
            }
        });

        thumbnailTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.thumbnail_background_iv).setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.thumbnail_background_iv).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
