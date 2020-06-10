package com.dustoreapplication.android.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dustoreapplication.android.logic.model.Order;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/8
 */
public class OrderViewModel extends ViewModel {

    private static final int STATUS_NO_PAY = 0;
    private static final int STATUS_HAS_PAY = 1;
    private static final int STATUS_NO_SEND = 2;
    private static final int STATUS_NO_TAKE = 3;
    private MutableLiveData<ArrayList<Order>> allOrders = new MutableLiveData<>();

    public void setAllOrder(ArrayList<Order> allOrder) {
        this.allOrders.setValue(allOrder);
    }

    public LiveData<ArrayList<Order>> getAllOrders(){
        return allOrders;
    }

    public ArrayList<Order> getNoPayOrders(){
        return getStatusOrders(STATUS_NO_PAY);
    }

    public ArrayList<Order> getNoSendOrders(){
        return getStatusOrders(STATUS_NO_SEND);
    }

    public ArrayList<Order> getNoTakeOrders(){
        return getStatusOrders(STATUS_NO_TAKE);
    }

    private ArrayList<Order> getStatusOrders(int status){
        ArrayList<Order> target = allOrders.getValue();
        if(target==null){
            return null;
        }
        ArrayList<Order> result = new ArrayList<>();
        for (Order order:target){
            if(order.getStatus()==status){
                result.add(order);
            }
        }
        return result;
    }
}
