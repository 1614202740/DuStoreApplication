package com.dustoreapplication.android.ui.activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dustoreapplication.android.DuApplication;
import com.dustoreapplication.android.logic.model.Address;
import com.dustoreapplication.android.logic.model.Order;
import com.dustoreapplication.android.logic.model.OrderItem;
import com.dustoreapplication.android.logic.model.vo.ConsigneeVo;
import com.dustoreapplication.android.logic.model.vo.ShippingVo;

import java.util.ArrayList;

/**
 * Created by 16142
 * on 2020/6/10
 * @author 16142
 */
public class OrderDetailViewModel extends ViewModel {
    private final MutableLiveData<ConsigneeVo> consignee = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<OrderItem>> orders = new MutableLiveData<>();
    private final MutableLiveData<ShippingVo> shipping = new MutableLiveData<>();
    private final MutableLiveData<String> sku = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Address>> addresses = new MutableLiveData<>();
    private final MutableLiveData<Integer> positionAddress = new MutableLiveData<>(0);
    private final MutableLiveData<Double> totalPrice = new MutableLiveData<>();
    private final MutableLiveData<String> orderId = new MutableLiveData<>();

    public void setConsignee(Address address){
        if(address==null){
            return;
        }
        ConsigneeVo consigneeVo = new ConsigneeVo();
        String consigneeName = address.getConsigneeName();
        consigneeVo.setName(consigneeName==null||consigneeName.length()==0?"匿名":consigneeName);
        consigneeVo.setAddress(address.getProvince()+address.getCity()+address.getArea());
        consigneeVo.setPhone(address.getPhone());
        this.consignee.setValue(consigneeVo);
    }

    public MutableLiveData<ConsigneeVo> getConsignee() {
        return consignee;
    }

    public void setOrders(ArrayList<OrderItem> goods){

        this.orders.setValue(goods);
    }
    public MutableLiveData<ArrayList<OrderItem>> getOrders() {
        return orders;
    }

    public void setShipping(Order order){
        if(order==null){
            return;
        }
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setTitle(order.getShippingName());
        shippingVo.setPrice(order.getPostFree());
        this.shipping.setValue(shippingVo);
    }

    public MutableLiveData<ShippingVo> getShipping() {
        return shipping;
    }

    public void setSku(String sku){
        this.sku.setValue(sku);
    }

    public MutableLiveData<String> getSku() {
        return sku;
    }

    public void setAddresses(ArrayList<Address> addresses){
        this.addresses.setValue(addresses);
    }

    public MutableLiveData<ArrayList<Address>> getAddresses() {
        return addresses;
    }

    public MutableLiveData<Integer> getPositionAddress() {
        return positionAddress;
    }

    public void setTotalPrice(ArrayList<OrderItem> items){
        double price = 0;
        for(OrderItem item:items){
            price += item.getTotalFee();
        }
        this.totalPrice.setValue(price);
    }

    public MutableLiveData<Double> getTotalPrice() {
        return totalPrice;
    }

    public void setOrderId(String orderId){
        this.orderId.setValue(orderId);
    }

    public MutableLiveData<String> getOrderId() {
        return orderId;
    }
}
