package com.dustoreapplication.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.Good;
import com.dustoreapplication.android.view.GoodGeneralInfoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16142
 * on 2020/6/6
 */
public class RelatedGoodAdapter extends RecyclerView.Adapter<RelatedGoodAdapter.ViewHolder> {

    private ArrayList<Good> data;
    private Context context;
    private int maxSize = 3;

    public RelatedGoodAdapter(ArrayList<Good> data, Context context){
        this.data = data;
        this.context = context;
    }

    public void setData(ArrayList<Good> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_related_good,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int row = position/3;
        LinearLayoutCompat linearLayout = holder.goodGeneralLinearLayout;
        for(int i=0; i<maxSize; ++i){
            int newPosition = row*3+i;
            if(newPosition>=data.size()){
                break;
            }
            Good good = data.get(newPosition);
            GoodGeneralInfoView infoView = new GoodGeneralInfoView(linearLayout.getContext(),good);
            LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT,1);
            infoView.setLayoutParams(layoutParams);
            infoView.setOnClickListener(v->{
                Intent intent = new Intent(context,GoodDetailActivity.class);
                intent.putExtra("good",good);
                context.startActivity(intent);
            });
            linearLayout.addView(infoView);
        }
    }

    @Override
    public int getItemCount() {
        return data==null?0:(int) Math.ceil((double) data.size()/maxSize);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayoutCompat goodGeneralLinearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodGeneralLinearLayout = itemView.findViewById(R.id.good_general_ll);
        }
    }
}
