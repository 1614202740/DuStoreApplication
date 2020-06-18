package com.dustoreapplication.android.ui.community.recommend.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.bean.LikeCustomer;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 16142
 * on 2020/5/13
 * @author 16142
 */
public class GoodHeadListAdapter extends RecyclerView.Adapter<GoodHeadListAdapter.ViewHolder> {

    private LikeCustomer[] resources;

    public GoodHeadListAdapter(LikeCustomer[] resources){
        this.resources = resources;
    }

    public void setResources(LikeCustomer[] resources) {
        this.resources = resources;
        notifyDataSetChanged();
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
        Glide.with(holder.itemView).load(resources[position].getAvatar()).into(holder.goodHeadCircleImageView);
    }

    @Override
    public int getItemCount() {
        return resources==null?0:resources.length;
    }
}
