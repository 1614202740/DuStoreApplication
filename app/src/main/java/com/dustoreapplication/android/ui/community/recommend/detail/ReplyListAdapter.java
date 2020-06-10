package com.dustoreapplication.android.ui.community.recommend.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 16142
 * on 2020/5/14
 * @author 16142
 */
public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListAdapter.ViewHolder> {

    private int size = new Random().nextInt(5);
    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView headCircleImageView;
        private AppCompatTextView nameTextView;
        private AppCompatTextView timeTextView;
        private AppCompatTextView contentTextView;
        private AppCompatImageButton goodImageButton;
        private AppCompatTextView goodCountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headCircleImageView = itemView.findViewById(R.id.reply_head_iv);
            nameTextView = itemView.findViewById(R.id.reply_name_tv);
            timeTextView = itemView.findViewById(R.id.reply_time_tv);
            contentTextView = itemView.findViewById(R.id.reply_content_tv);
            goodImageButton = itemView.findViewById(R.id.reply_good_btn);
            goodCountTextView = itemView.findViewById(R.id.reply_good_count_tv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(R.mipmap.topic_title_test).into(holder.headCircleImageView);
        holder.nameTextView.setText("匿名用户");
        holder.timeTextView.setText("3天前");
        holder.contentTextView.setText("鞋子本无价，喜欢就入手。");
        holder.goodImageButton.setOnClickListener(v->{
            v.setBackgroundResource(R.drawable.ic_heart_fill_red_24dp);
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }
}
