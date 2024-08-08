package com.phonepe.tables;

import com.phonepe.models.Order;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class OrderBookTable {
    private final Map<String, PriorityQueue<Order>> orderBooks = new HashMap<>();

    public void addOrder(Order order) {
        PriorityQueue<Order> q = orderBooks.get(order.getStockId());
        if (q == null) {
            q = new PriorityQueue<>(Comparator.comparing(Order::getAcceptedTimestamp));
        }
        q.add(order);
        orderBooks.put(order.getStockId(), q);
    }

    public PriorityQueue<Order> getOrderBookForStockId(String stockId) {
        return orderBooks.get(stockId);
    }

    public Map<String, PriorityQueue<Order>> getOrderBook() {
        return orderBooks;
    }
}
