package com.dustoreapplication.android.ui.order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dustoreapplication.android.R;
import com.dustoreapplication.android.logic.model.Order;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOrdersFragment extends Fragment {

    private RecyclerView itemRecyclerView;

    private OrderViewModel mViewModel;
    private OrderListAdapter mAdapter;

    private static final String ARG_STATUS  = "status";

    private int status;

    public ListOrdersFragment() {
        // Required empty public constructor
    }

    public ListOrdersFragment(int status){
        this.status = status;
    }

    public static ListOrdersFragment newInstance(int status) {
        ListOrdersFragment fragment = new ListOrdersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STATUS,status);
        fragment.setArguments(args);
        return new ListOrdersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getInt(ARG_STATUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_orders, container, false);
        initView(root);
        mViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        mViewModel.getAllOrders().observe(getViewLifecycleOwner(),orders -> {
            ArrayList<Order> result;
            switch (status){
                case 0:
                    result = orders;
                    break;
                case 1:
                    result = mViewModel.getNoPayOrders();
                    break;
                case 2:
                    result = mViewModel.getNoSendOrders();
                    break;
                case 3:
                    result = mViewModel.getNoTakeOrders();
                    break;
                default:
                    return;
            }
            if(mAdapter==null){
                mAdapter = new OrderListAdapter(result);
                itemRecyclerView.setAdapter(mAdapter);
            }else {
                mAdapter.setOrders(result);
            }
        });
        return root;
    }

    private void initView(View root){
        itemRecyclerView = root.findViewById(R.id.order_list_rv);
    }
}
