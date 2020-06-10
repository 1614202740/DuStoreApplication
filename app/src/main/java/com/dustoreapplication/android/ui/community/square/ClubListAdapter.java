package com.dustoreapplication.android.ui.community.square;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 16142
 * on 2020/5/15
 * @author 16142
 */
public class ClubListAdapter extends RecyclerView.Adapter<ClubListAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_club_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(R.mipmap.topic_title_test).into(holder.avatarCircleImageView);
        holder.titleTextView.setText("来一起下厨房吧");
        holder.infoTextView.setText("最有人间烟火的地方，就是厨房啦...");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView avatarCircleImageView;
        private AppCompatTextView titleTextView;
        private AppCompatTextView infoTextView;
        private AppCompatButton joinButton;
        private AppCompatTextView countTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarCircleImageView = itemView.findViewById(R.id.club_avatar_iv);
            titleTextView = itemView.findViewById(R.id.club_title_tv);
            infoTextView = itemView.findViewById(R.id.club_info_tv);
            joinButton = itemView.findViewById(R.id.club_join_btn);
            countTextView = itemView.findViewById(R.id.club_count_tv);
        }
    }
}
