package com.dustoreapplication.android.ui.community.recommend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.bean.Dynamic;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 16142
 * on 2020/5/13
 * @author 16142
 */
public class RecommendCardAdapter extends RecyclerView.Adapter<RecommendCardAdapter.ViewHolder>{

    private OnItemClickListener listener;
    private ArrayList<Dynamic> dynamics;

    public void setDynamics(ArrayList<Dynamic> dynamics) {
        this.dynamics = dynamics;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public RecommendCardAdapter(ArrayList<Dynamic> dynamics){
        this.dynamics = dynamics;
        notifyDataSetChanged();
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
        Dynamic dynamic = dynamics.get(position);
        if(dynamic!=null) {
            Glide.with(holder.cardImageView).load(dynamic.getImageUrl()[0]).into(holder.cardImageView);
            holder.cardTitleTextView.setText(dynamic.getTitle());
            holder.goodImageButton.setOnClickListener(v -> {
                v.setBackgroundResource(R.drawable.ic_good_fill_red_24dp);
            });
            Glide.with(holder.publisherHeadCircleImageView).load(dynamic.getUserVo().getAvatar()).into(holder.publisherHeadCircleImageView);
            holder.publisherNameTextView.setText(dynamic.getUserVo().getUsername());
            holder.goodCountTextView.setText(String.valueOf(dynamic.getLikeCount()));
            holder.cardImageView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dynamics==null?0:dynamics.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
