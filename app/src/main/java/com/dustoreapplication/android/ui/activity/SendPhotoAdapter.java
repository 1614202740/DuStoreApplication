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

class SendPhotoAdapter extends RecyclerView.Adapter<SendPhotoAdapter.ViewHolder>{

    private List<ImageModel> ImageModelList;


    static class ViewHolder extends RecyclerView.ViewHolder{
        SquareImageView photoImage;

        public ViewHolder (View view)
        {
            super(view);
            photoImage =  view.findViewById(R.id.dynamics_bottom_photo);
        }
    }

    public SendPhotoAdapter(List<ImageModel> ImageModelList){
        this.ImageModelList = ImageModelList;
    }

    @NonNull
    @Override
    public SendPhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_photo,parent,false);
        SendPhotoAdapter.ViewHolder holder = new SendPhotoAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SendPhotoAdapter.ViewHolder holder, int position) {
        Bitmap photo = BitmapFactory.decodeFile(ImageModelList.get(position).getPath());
        holder.photoImage.setImageBitmap(photo);
    }

    @Override
    public int getItemCount() {
        return ImageModelList.size();
    }
}
