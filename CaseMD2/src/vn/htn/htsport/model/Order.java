package vn.htn.htsport.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private long id;
    private String name;
    private String phone;
    private String address;

    List<OrderItem>orderItems = new ArrayList<>();

    public Order() {
    }

    public static  Order parse(String record) {
        Order order = new Order();
        String[] field = record.split(",");
        order.id = Long.parseLong(field[0]);
        order.name = field[1];
        order.phone = field[2];
        order.address = field[3];
        return order;
    }

    public Order(long id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + phone + "," + address;
    }
}
