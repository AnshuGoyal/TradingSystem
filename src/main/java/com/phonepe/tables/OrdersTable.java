package com.phonepe.tables;

import com.phonepe.models.Order;

import java.util.HashMap;
import java.util.Map;

public class OrdersTable {
    private final Map<String, Order> orders = new HashMap<>();

    public Order addOrder(Order order) {
        return orders.put(order.getOrderId(), order);
    }

    public Order removeOrder(String orderId) {
        return orders.remove(orderId);
    }

    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }
}
