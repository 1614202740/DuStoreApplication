package com.dustoreapplication.android.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ImageViewCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.view.SquareImageView;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<ImageModel> ImageModelList;
    private List<ImageModel> selectImageList;
    private PhotoActivity photoActivity;

    private PhotoViewModel mViewModel;

    static class ViewHolder extends RecyclerView.ViewHolder{
        SquareImageView photoImage;
        AppCompatImageView dynamics_photo_button;


        public ViewHolder (View view)
        {
            super(view);
            photoImage =  view.findViewById(R.id.dynamics_photo);
            dynamics_photo_button = view.findViewById(R.id.dynamics_photo_button);
        }

    }

    public PhotoAdapter (List<ImageModel> ImageModelList,List<ImageModel> selectImageList,PhotoActivity photoActivity){
        this.ImageModelList = ImageModelList;
        this.selectImageList = selectImageList;
        this.photoActivity = photoActivity;
        this.mViewModel = new ViewModelProvider(this.photoActivity).get(PhotoViewModel.class);
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic_photo,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, int position) {
        Bitmap photo = BitmapFactory.decodeFile(ImageModelList.get(position).getPath());
        holder.photoImage.setImageBitmap(photo);
        holder.dynamics_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if(ImageModelList.get(position).isSelected() == false){
                    if(selectImageList.size() < 6){
                        ImageModelList.get(position).setSelected(true);
                        Glide.with(holder.itemView).load(R.drawable.shape_photo_select).into(holder.dynamics_photo_button);
                        selectImageList.add(ImageModelList.get(position));
                        mViewModel.addSize();
                    }
                }else{
                    ImageModelList.get(position).setSelected(false);
                    Glide.with(holder.itemView).load(R.drawable.shape_photo_cancel).into(holder.dynamics_photo_button);
                    selectImageList.remove(ImageModelList.get(position));
                    mViewModel.subSize();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ImageModelList.size();
    }
}
