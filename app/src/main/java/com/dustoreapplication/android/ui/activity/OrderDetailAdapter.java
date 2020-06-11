package com.dustoreapplication.android.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.OrderItem;

import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/11
 */
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private ArrayList<OrderItem> data;

    public OrderDetailAdapter(ArrayList<OrderItem> data) {
        this.data = data;
    }

    public void setData(ArrayList<OrderItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_no_button_info,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem item = data.get(position);
        Glide.with(holder.itemView).load(item.getPicPath()).into(holder.thumbnailImageView);
        holder.titleTextView.setText(item.getTitle());
        holder.sizeTextView.setText("默认");
        holder.countTextView.setText("数量x"+item.getNum());
        Glide.with(holder.itemView).load(R.mipmap.ic_logo).into(holder.logoImageView);
        holder.priceTextView.setText(String.valueOf(item.getTotalFee()));
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView thumbnailImageView;
        private AppCompatTextView titleTextView;
        private AppCompatTextView sizeTextView;
        private AppCompatTextView countTextView;
        private AppCompatImageView logoImageView;
        private AppCompatTextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.order_detail_thumbnail_iv);
            titleTextView = itemView.findViewById(R.id.order_detail_title_tv);
            sizeTextView = itemView.findViewById(R.id.order_detail_sku_tv);
            countTextView = itemView.findViewById(R.id.order_detail_count_tv);
            logoImageView = itemView.findViewById(R.id.order_detail_logo_iv);
            priceTextView = itemView.findViewById(R.id.order_detail_price_tv);
        }
    }
}
