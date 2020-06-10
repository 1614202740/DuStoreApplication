package com.dustoreapplication.android.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dustoreapplication.android.ComparableFutureTask;
import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.PanelSlide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16142
 * on 2020/6/2
 * @author 16142
 */
public class SlideShowImageView extends FrameLayout {

    private List<PanelSlide> data = new ArrayList<>();
    private ViewPager2 loopViewPager;
    private LinearLayoutCompat dotsLinearLayout;
    private AppCompatTextView titleTextView;
    private int previousSelectedPosition = 0;
    private boolean isRunning = false;
    private RecyclerView.Adapter adapter;
    private Context context;

    /**
     * 每5s后进行轮播，通知ViewPager进行改变
     */
    private Handler mHandler = new Handler(msg -> {
        loopViewPager.setCurrentItem(msg.arg1);
        return true;
    });

    /**
     * 轮播图设置数据之后的消息通道，通知原点以及ViewPager进行
     * 相应更新
     */
    private Handler loopHandler = new Handler(msg -> {
        adapter.notifyDataSetChanged();
        initDotLinearLayout();
        initPageChangeListener();
        return true;
    });

    public SlideShowImageView(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SlideShowImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_slideshow_imageview, this);
        loopViewPager = view.findViewById(R.id.slideshow_loop_vp2);
        dotsLinearLayout = view.findViewById(R.id.slideshow_dots_ll);
        titleTextView = view.findViewById(R.id.slideshow_title_tv);
        adapter = new SlideShowAdapter(data);
        loopViewPager.setAdapter(adapter);
        previousSelectedPosition = 0;

        DuApplication.THREAD_POOL_EXECUTOR.execute(() -> {
            isRunning = true;
            while (isRunning) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshSlideShow();
            }
        });
    }

    public void setData(List<PanelSlide> slides) {
        data.addAll(slides);
        loopHandler.sendMessage(Message.obtain());
    }

    /**
     * 初始化圆点布局
     */
    private void initDotLinearLayout(){
        View dotView;
        LinearLayoutCompat.LayoutParams layoutParams;
        for(int i=0; i<data.size(); ++i){
            dotView = new View(context);
            dotView.setBackgroundResource(R.drawable.ic_dot_8dp);
            layoutParams = new LinearLayoutCompat.LayoutParams(8,8);
            layoutParams.setMarginStart(8);
            dotView.setEnabled(false);
            dotsLinearLayout.addView(dotView,layoutParams);
        }
        dotsLinearLayout.getChildAt(0).setEnabled(true);
    }

    /**
     * 初始化自动轮播
     */
    private void initPageChangeListener(){
        int m = (Integer.MAX_VALUE/2)%data.size();
        int currentPosition = Integer.MAX_VALUE/2-m;
        loopViewPager.setCurrentItem(currentPosition);

        loopViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int newPosition = position % data.size();
                dotsLinearLayout.getChildAt(previousSelectedPosition).setEnabled(false);
                dotsLinearLayout.getChildAt(newPosition).setEnabled(true);
                previousSelectedPosition = newPosition;
            }
        });
    }

    /**
     * 通知轮播图进行更新
     */
    private void refreshSlideShow(){
        Message message = Message.obtain();
        message.arg1 = loopViewPager.getCurrentItem()+1;
        mHandler.sendMessage(message);
    }
}
