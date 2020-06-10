package com.dustoreapplication.android.ui.personal;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.ui.personal.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotLoggedInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotLoggedInFragment extends Fragment {

    private AppCompatTextView phoneButton;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NotLoggedInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotLoggedInFragment newInstance() {
        return new NotLoggedInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_not_logged_in, container, false);
        initView(view);
        phoneButton.setOnClickListener(v-> startActivity(new Intent(this.getActivity(), LoginActivity.class)));
        return view;
    }

    private void initView(View root){
        phoneButton = root.findViewById(R.id.personal_login_phone_btn);
    }
}
