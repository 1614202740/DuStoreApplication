package com.dustoreapplication.android.ui.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.vo.GoodSelectVo;
import com.dustoreapplication.android.view.SquareImageView;

import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/10
 */
public class
GoodSelectAdapter extends RecyclerView.Adapter<GoodSelectAdapter.ViewHolder> {

    private ArrayList<GoodSelectVo> data;

    public GoodSelectAdapter(ArrayList<GoodSelectVo> data) {
        this.data = data;
    }

    public void setData(ArrayList<GoodSelectVo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_good_select,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodSelectVo data = this.data.get(position);
        Glide.with(holder.itemView).load(data.getPicture()).into(holder.thumbnailImageView);
        holder.titleTextView.setText(data.getTitle());
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext().getString(R.string.good_select_receiver));
            intent.putExtra("position",position);
            v.getContext().sendBroadcast(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data ==null?0: data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SquareImageView thumbnailImageView;
        AppCompatTextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.good_select_thumbnail_iv);
            titleTextView = itemView.findViewById(R.id.good_select_title_tv);
        }
    }
}
