package com.dustoreapplication.android.ui.community.recommend.detail;

import android.content.Context;
import android.telephony.RadioAccessSpecifier;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private Context context = null;
    private int size = new Random().nextInt(5);

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView headCircleImageView;
        private AppCompatTextView nameTextView;
        private AppCompatTextView timeTextView;
        private AppCompatTextView contentTextView;
        private AppCompatTextView moreTextView;
        private AppCompatImageButton goodImageButton;
        private AppCompatTextView goodCountTextView;
        private RecyclerView replyRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headCircleImageView = itemView.findViewById(R.id.comment_head_iv);
            nameTextView = itemView.findViewById(R.id.comment_name_tv);
            timeTextView = itemView.findViewById(R.id.comment_time_tv);
            contentTextView = itemView.findViewById(R.id.comment_content_tv);
            moreTextView = itemView.findViewById(R.id.comment_more_btn);
            goodImageButton = itemView.findViewById(R.id.comment_good_btn);
            goodCountTextView = itemView.findViewById(R.id.comment_good_count_tv);
            replyRecyclerView = itemView.findViewById(R.id.comment_reply_rv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(R.mipmap.topic_title_test).into(holder.headCircleImageView);
        holder.nameTextView.setText("恶犬小奶包");
        holder.timeTextView.setText("3月19日");
        holder.contentTextView.setText("不好意思，我真的觉得后脚跟漏出来穿鞋的无言以对..");
        holder.goodImageButton.setOnClickListener(v->{
            v.setBackgroundResource(R.drawable.ic_heart_fill_red_24dp);
        });
        if(context!=null){
            holder.replyRecyclerView.setAdapter(new ReplyListAdapter());
            holder.replyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }
}
