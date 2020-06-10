package com.dustoreapplication.android.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.PanelSlide;

import java.util.List;

/**
 * Created by 16142
 * on 2020/6/2
 * @author 16142
 */
public class SlideShowAdapter extends RecyclerView.Adapter<SlideShowAdapter.ViewHolder> {

    private List<PanelSlide> data;

    public SlideShowAdapter(List<PanelSlide> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slideshow,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(data.size()!=0) {
            int newPosition = position % data.size();
            PanelSlide slide = data.get(newPosition);
            RequestBuilder<Drawable> builder = Glide.with(holder.itemView).load(Uri.parse(slide.getPicUrl()));
            if(slide.getPicUrl2()!=null){
                builder = builder.load(Uri.parse(slide.getPicUrl2()));
            }
            if (slide.getPicUrl()!=null){
                builder = builder.load(Uri.parse(slide.getPicUrl()));
            }
            builder.into((AppCompatImageView) holder.itemView);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
         ViewHolder(@NonNull View itemView) {
             super(itemView);
         }
     }
}
