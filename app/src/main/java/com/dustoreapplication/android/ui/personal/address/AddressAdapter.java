package com.dustoreapplication.android.ui.personal.address;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.Address;
import com.dustoreapplication.android.logic.service.AddressIntentService;
import com.dustoreapplication.android.logic.service.CustomerIntentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16142
 * on 2020/5/30
 * @author 16142
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<Address> addresses;
    private List<AppCompatRadioButton> radioButtons;
    private Context context;

    public AddressAdapter(Context context, List<Address> addresses) {
        this.addresses = addresses;
        this.context = context;
        this.radioButtons = new ArrayList<>();
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_info, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = addresses.get(position);
        if(address!=null) {
            holder.nameTextView.setText(address.getUserId());
            holder.phoneTextView.setText(new StringBuilder(address.getPhone()).replace(3, 7, "****"));
            holder.detailTextView.setText(address.getProvince() + address.getCity() + address.getArea() + address.getDetails());
            holder.defaultButton.setChecked(address.getIsDefault());
            holder.defaultButton.setOnClickListener(v -> notifyOtherButtons(position));
            holder.deleteButton.setOnClickListener(v-> AddressIntentService.startActionDelete(context,address.getAddressId()));
            radioButtons.add(holder.defaultButton);
        }
    }

    @Override
    public int getItemCount() {
        return addresses == null ? 0 : addresses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView nameTextView;
        private AppCompatTextView phoneTextView;
        private AppCompatTextView detailTextView;
        private AppCompatRadioButton defaultButton;
        private AppCompatTextView editButton;
        private AppCompatTextView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.address_name_tv);
            phoneTextView = itemView.findViewById(R.id.address_phone_tv);
            detailTextView = itemView.findViewById(R.id.address_detail_tv);
            defaultButton = itemView.findViewById(R.id.address_default_btn);
            editButton = itemView.findViewById(R.id.address_edit_btn);
            deleteButton = itemView.findViewById(R.id.address_delete_btn);
        }
    }

    private void notifyOtherButtons(int position){
        for (int i=0; i<radioButtons.size(); i++){
            if(i!=position){
                radioButtons.get(i).setChecked(false);
            }
        }
    }
}
