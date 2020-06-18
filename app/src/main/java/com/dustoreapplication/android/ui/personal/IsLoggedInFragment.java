package com.dustoreapplication.android.ui.personal;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.bean.Customer;
import com.dustoreapplication.android.ui.personal.space.SpaceActivity;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IsLoggedInFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class IsLoggedInFragment extends Fragment {

    private AppCompatTextView usernameTextView;
    private CircleImageView avatarImageView;
    private AppCompatImageView attendanceImageView;
    private AppCompatImageView creationImageView;
    private LinearLayoutCompat infoLinearLayout;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment IsLoggedInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IsLoggedInFragment newInstance() {
        return new IsLoggedInFragment();
    }
    public IsLoggedInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_is_logged_in, container, false);
        initView(view);
        Glide.with(view).load(R.mipmap.topic_title_test).into(attendanceImageView);
        Glide.with(view).load(R.mipmap.topic_title_test).into(creationImageView);
        infoLinearLayout.setOnClickListener(v-> SpaceActivity.startActivity(this.getContext()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Customer customer = DuApplication.customer;
        if(customer!=null){
            usernameTextView.setText(customer.getUsername());
            Glide.with(this).load(customer.getAvatar()).into(avatarImageView);
        }
    }

    private void initView(View root){
        usernameTextView = root.findViewById(R.id.personal_data_username_tv);
        avatarImageView = root.findViewById(R.id.personal_data_avatar_iv);
        attendanceImageView = root.findViewById(R.id.personal_data_attendance_iv);
        creationImageView = root.findViewById(R.id.personal_data_creation_iv);
        infoLinearLayout = root.findViewById(R.id.personal_data_info_ll);
    }
}
