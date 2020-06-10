package com.dustoreapplication.android.ui.community.recommend.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 16142
 * on 2020/5/13
 * @author 16142
 */
public class GoodHeadListAdapter extends RecyclerView.Adapter<GoodHeadListAdapter.ViewHolder> {

    List<Integer> resources;

    public GoodHeadListAdapter(List<Integer> resources){
        this.resources = resources;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView goodHeadCircleImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodHeadCircleImageView = itemView.findViewById(R.id.recommend_detail_good_head_iv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circleimageview_good_head,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(resources.get(position)).into(holder.goodHeadCircleImageView);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
