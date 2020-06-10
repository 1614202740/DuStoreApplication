package com.dustoreapplication.android.ui.community.recommend.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;

import java.util.List;

/**
 * Created by 16142
 * on 2020/5/13
 * @author 16142
 */
public class ThumbnailListAdapter extends RecyclerView.Adapter<ThumbnailListAdapter.ViewHolder> {

    private List<Integer> resources;
    private AppCompatImageView showImageView;

    public ThumbnailListAdapter(List<Integer> resources, AppCompatImageView showImageView){
        this.resources = resources;
        this.showImageView = showImageView;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView thumbnailImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnail_iv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageview_detail_thumbnail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(resources.get(position)).into(holder.thumbnailImageView);
        holder.thumbnailImageView.setOnClickListener(v->{
            Glide.with(showImageView).load(resources.get(position)).into(showImageView);
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
