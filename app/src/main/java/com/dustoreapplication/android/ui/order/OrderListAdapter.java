package com.dustoreapplication.android.ui.order;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.bean.Order;
import com.dustoreapplication.android.logic.model.bean.OrderItem;

import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/8
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private ArrayList<Order> orders;

    private static final String[] STATUS_TITLES = new String[]{
            "当前订单未支付",
            "已支付",
            "待发货",
            "待收货",
            "交易成功",
            "交易关闭",
            "交易失败"
    };

    public OrderListAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders.clear();
        this.orders.addAll(orders);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_info,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        if(order.getItem().size()>0){
            OrderItem item = order.getItem().get(0);
            holder.statusTextView.setText(STATUS_TITLES[order.getStatus()]);
            Glide.with(holder.itemView).load(item.getPicPath()).into(holder.thumbnailImageView);
            holder.titleTextView.setText(item.getTitle());
            holder.countTextView.setText("数量x"+item.getNum());
            holder.sizeTextView.setText("43");
            Glide.with(holder.itemView).load(R.mipmap.ic_logo).into(holder.logoTextView);
            holder.priceTextView.setText("￥"+order.getPayment());
            switch (order.getStatus()){
                case 0:
                    holder.handleButton.setText("现在付款");
                    break;
                case 1:
                case 2:
                case 4:
                case 5:
                    holder.handleButton.setVisibility(View.GONE);
                    break;
                case 3:
                    holder.handleButton.setText("确认收货");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return orders==null?0:orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView statusTextView;
        private AppCompatImageView thumbnailImageView;
        private AppCompatTextView titleTextView;
        private AppCompatTextView sizeTextView;
        private AppCompatTextView countTextView;
        private AppCompatImageView logoTextView;
        private AppCompatTextView priceTextView;
        private AppCompatButton handleButton;
        private AppCompatButton cancelButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statusTextView = itemView.findViewById(R.id.order_info_status_tv);
            thumbnailImageView = itemView.findViewById(R.id.order_info_thumbnail_iv);
            titleTextView = itemView.findViewById(R.id.order_info_title_tv);
            sizeTextView = itemView.findViewById(R.id.order_info_size_tv);
            countTextView = itemView.findViewById(R.id.order_info_count_tv);
            logoTextView = itemView.findViewById(R.id.order_info_logo_iv);
            priceTextView = itemView.findViewById(R.id.order_info_price_tv);
            handleButton = itemView.findViewById(R.id.order_info_handle_btn);
            cancelButton = itemView.findViewById(R.id.order_info_cancel_btn);
        }
    }
}
