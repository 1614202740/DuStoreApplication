package com.dustoreapplication.android.ui.personal.space;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.ui.activity.PhotoActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DynamicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DynamicFragment extends Fragment {

    private AppCompatButton newButton;

    public DynamicFragment() {
        // Required empty public constructor
    }

    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dynamic, container, false);
        initView(root);
        newButton.setOnClickListener(v-> PhotoActivity.startActivity(this.getContext()));
        return root;
    }

    private void initView(View root){
        newButton = root.findViewById(R.id.space_dynamic_new_btn);
    }
}
