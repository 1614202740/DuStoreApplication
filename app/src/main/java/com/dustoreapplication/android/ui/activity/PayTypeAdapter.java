package com.dustoreapplication.android.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.vo.PayTypeVo;

import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/11
 */
public class PayTypeAdapter extends RecyclerView.Adapter<PayTypeAdapter.ViewHolder> {

    private ArrayList<PayTypeVo> data;
    private ArrayList<RadioButton> buttons = new ArrayList<>();
    private CheckStandViewModel mViewModel;
    private int position = 0;

    public PayTypeAdapter(ArrayList<PayTypeVo> data, CheckStandViewModel mViewModel, LifecycleOwner lifecycleOwner) {
        this.data = data;
        this.mViewModel = mViewModel;
        this.mViewModel.getType().observe(lifecycleOwner,type->{
            for (RadioButton button:buttons){
                button.setChecked(false);
            }
            if(type<buttons.size()) {
                buttons.get(type).setChecked(true);
            }
        });
    }

    public void setData(ArrayList<PayTypeVo> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public int getPosition() {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay_type,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PayTypeVo vo = data.get(position);
        Glide.with(holder.itemView).load(vo.getLogoId()).into(holder.logoImageView);
        holder.titleTextView.setText(vo.getTitle()+"支付");
        buttons.add(holder.checkRadioButton);
        if(this.position==position){
            mViewModel.setType(position);
        }
        holder.checkRadioButton.setOnClickListener(v->{
            mViewModel.setType(position);
        });
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView logoImageView;
        private AppCompatTextView titleTextView;
        private RadioButton checkRadioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logoImageView = itemView.findViewById(R.id.pay_type_logo_iv);
            titleTextView = itemView.findViewById(R.id.pay_type_title_tv);
            checkRadioButton = itemView.findViewById(R.id.pay_type_check_btn);
        }
    }
}
