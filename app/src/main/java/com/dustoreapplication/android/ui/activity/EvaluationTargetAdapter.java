package com.dustoreapplication.android.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.dustoreapplication.android.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 16142
 * on 2020/6/7
 */
public class EvaluationTargetAdapter extends RecyclerView.Adapter<EvaluationTargetAdapter.ViewHolder> {

    private ArrayList<String> titles;
    private ArrayList<Integer> counts;

    public void setData(ArrayList titles, ArrayList count){
        this.titles.clear();
        this.counts.clear();
        this.titles.addAll(titles);
        this.counts.addAll(count);
        notifyDataSetChanged();

    }
    public EvaluationTargetAdapter(ArrayList titles, ArrayList count){
        this.titles = titles;
        this.counts = count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation_target,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTextView.setText(titles.get(position));
        holder.countTextView.setText(String.valueOf(counts.get(position)));
    }

    @Override
    public int getItemCount() {
        return (titles==null||counts==null)?0:titles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView titleTextView;
        private AppCompatTextView countTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.evaluation_target_title_tv);
            countTextView = itemView.findViewById(R.id.evaluation_target_count_tv);
        }
    }
}
