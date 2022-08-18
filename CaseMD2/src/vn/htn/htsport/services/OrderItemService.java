package vn.htn.htsport.services;

import vn.htn.htsport.model.OrderItem;
import vn.htn.htsport.utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderItemService implements IOrderItemService {
    List<OrderItem> orderItems = new ArrayList<>();
    public static String path = "data/order-items.csv";

    private static OrderItemService instance;

    private OrderItemService(){}
    public static OrderItemService getInstance() {
        if (instance == null)
            instance = new OrderItemService();
        return instance;
    }


    @Override
    public List<OrderItem> getOrderItems() {
        List<OrderItem> newOrderItems = new ArrayList<>();
        List<String> records = CSVUtils.read(path);
        for (String record : records) {
            newOrderItems.add(new OrderItem(record));
        }
        return orderItems = newOrderItems;
    }

    @Override
    public void add(OrderItem newOrderItem) {
        orderItems.add(newOrderItem);
        CSVUtils.write(path, orderItems);
    }

    @Override
    public void update(OrderItem newOrderItem) {
        List<OrderItem> orderItems = getOrderItems();
        CSVUtils.write(path, orderItems);
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getId() == id)
                return orderItem;
        }
        return null;
    }
}
