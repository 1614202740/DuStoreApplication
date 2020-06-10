package com.dustoreapplication.android.ui.community.square;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;

import java.util.Random;

/**
 * Created by 16142
 * on 2020/5/25
 * @author 16142
 */
public class ThumbnailListAdapter extends RecyclerView.Adapter<ThumbnailListAdapter.ViewHolder> {

    private final int count = 10+(new Random().nextInt(10));
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_square_thumbnail ,parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.imageView).load(R.mipmap.topic_title_test).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.square_thumbnail_iv);
        }
    }
}
