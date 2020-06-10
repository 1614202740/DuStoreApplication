package com.dustoreapplication.android.ui.community.recommend;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.ui.community.recommend.detail.RecommendDetailActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 16142
 * on 2020/5/13
 * @author 16142
 */
public class RecommendCardAdapter extends RecyclerView.Adapter<RecommendCardAdapter.ViewHolder>{

    private OnItemClickListener listener;
    public String username = "鹿其鹿璘";

    public interface OnItemClickListener{
        void onClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView cardImageView;
        AppCompatTextView cardTitleTextView;
        CircleImageView publisherHeadCircleImageView;
        AppCompatTextView publisherNameTextView;
        AppCompatImageButton goodImageButton;
        AppCompatTextView goodCountTextView;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.recommend_card_iv);
            cardTitleTextView = itemView.findViewById(R.id.recommend_card_title_tv);
            publisherHeadCircleImageView = itemView.findViewById(R.id.recommend_card_publisher_head_iv);
            publisherNameTextView = itemView.findViewById(R.id.recommend_card_publisher_name_tv);
            goodImageButton = itemView.findViewById(R.id.recommend_card_good_btn);
            goodCountTextView = itemView.findViewById(R.id.recommend_card_good_count_tv);
            cardView = itemView.findViewById(R.id.recommend_card_cv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.cardImageView).load(R.mipmap.topic_title_test).into(holder.cardImageView);
        holder.cardTitleTextView.setText("#Converse x golf le fleur # 海盐奶油巧克力的香味");
        holder.goodImageButton.setOnClickListener(v -> {
            v.setBackgroundResource(R.drawable.ic_good_fill_red_24dp);
        });
        Glide.with(holder.publisherHeadCircleImageView).load(R.mipmap.topic_title_test).into(holder.publisherHeadCircleImageView);
        holder.publisherNameTextView.setText(username);
        holder.goodCountTextView.setText("453");
        holder.cardImageView.setOnClickListener(v->{
            if(listener!=null) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
