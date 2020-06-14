package com.dustoreapplication.android.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.view.SquareImageView;

import java.util.List;

public class SelectedPhotoAdapter extends  RecyclerView.Adapter<SelectedPhotoAdapter.ViewHolder>{
    private List<ImageModel> ImageModelList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        SquareImageView photoImage;


        public ViewHolder (View view)
        {
            super(view);
            photoImage =  view.findViewById(R.id.dynamics_bottom_photo);
        }
    }

    public SelectedPhotoAdapter(List<ImageModel> ImageModelList){
        this.ImageModelList = ImageModelList;
    }

    @NonNull
    @Override
    public SelectedPhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic_selected_photo,parent,false);
        SelectedPhotoAdapter.ViewHolder holder = new SelectedPhotoAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedPhotoAdapter.ViewHolder holder, int position) {
        Bitmap photo = BitmapFactory.decodeFile(ImageModelList.get(position).getPath());
        holder.photoImage.setImageBitmap(photo);
    }

    @Override
    public int getItemCount() {
        return ImageModelList.size();
    }
}
