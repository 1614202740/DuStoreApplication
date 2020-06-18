package com.dustoreapplication.android.ui.square.store;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.bean.Good;
import com.dustoreapplication.android.ui.activity.GoodDetailActivity;

import java.util.List;

/**
 * Created by 16142
 * on 2020/5/26
 */
public class MainGoodAdapter extends RecyclerView.Adapter<MainGoodAdapter.ViewHolder> {

    private List<Good> goods;
    private Context context;

    public void setGoods(List<Good> goods){
        this.goods = goods;
        notifyDataSetChanged();
    }

    public MainGoodAdapter(List<Good> goods, Context context) {
        this.goods = goods;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_good_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Good good = goods.get(position);
        holder.titleTextView.setText(good.getTitle());
        holder.priceTextView.setText("￥"+good.getPrice());
        holder.countTextView.setText(good.getNum()+"人已购买");
        Glide.with(holder.itemView).load(Uri.parse(good.getImage()[0])).into(holder.thumbnailImageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GoodDetailActivity.class);
            intent.putExtra("good",good);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return goods==null?0:goods.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView thumbnailImageView;
        AppCompatTextView titleTextView;
        AppCompatTextView priceTextView;
        AppCompatTextView countTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.good_thumbnail_iv);
            titleTextView = itemView.findViewById(R.id.good_title_tv);
            priceTextView = itemView.findViewById(R.id.good_price_tv);
            countTextView = itemView.findViewById(R.id.good_purchase_count_tv);
        }
    }
}
