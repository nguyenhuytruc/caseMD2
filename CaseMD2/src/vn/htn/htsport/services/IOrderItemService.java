package vn.htn.htsport.services;

import vn.htn.htsport.model.OrderItem;

import java.util.List;

public interface IOrderItemService {
    List<OrderItem> getOrderItems();

    void add(OrderItem newOrderItem);

    void update(OrderItem newOrderItem);

    OrderItem getOrderItemById(int id);
}
