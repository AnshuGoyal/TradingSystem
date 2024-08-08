package com.phonepe.services;

import com.phonepe.models.Order;
import com.phonepe.models.OrderStatus;
import com.phonepe.strategy.TradeMatchingStrategy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class OrderService {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final DbService dbService;
    private final TradeMatchingStrategy tradeMatchingStrategy;

    public OrderService(DbService dbService, TradeMatchingStrategy tradeMatchingStrategy) {
        this.dbService = dbService;
        this.tradeMatchingStrategy = tradeMatchingStrategy;
    }

    public void placeOrder(Order order) {
        lock.writeLock().lock();
        try {
            dbService.addOrder(order);
            dbService.addToOrderBook(order);
            System.out.println("Order placed: " + order);
            matchOrders(order);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void modifyOrder(String orderId, int newQuantity, double newPrice) {
        lock.writeLock().lock();
        try {
            Order oldOrder = dbService.getOrder(orderId);
            if (oldOrder != null && oldOrder.getStatus() == OrderStatus.ACCEPTED) {
                Order newOrder = new Order(oldOrder.getOrderId(), oldOrder.getAccountId(), oldOrder.getStockId(),
                        oldOrder.getOrderType(), newQuantity, newPrice, Instant.now());
                dbService.addOrder(newOrder);

                PriorityQueue<Order> orderBook = dbService.getOrderBookForStockId(oldOrder.getStockId());
                orderBook.remove(oldOrder);
                orderBook.add(newOrder);
                System.out.println("Order modified: " + newOrder);
                matchOrders(newOrder);
            } else {
                System.out.println("Order cannot be modified or does not exist.");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void cancelOrder(String orderId) {
        lock.writeLock().lock();
        try {
            Order order = dbService.getOrder(orderId);
            if (order != null && order.getStatus() == OrderStatus.ACCEPTED) {
                order.setStatus(OrderStatus.CANCELLED);

                PriorityQueue<Order> orderBook = dbService.getOrderBookForStockId(order.getStockId());
                orderBook.remove(order);
                System.out.println("Order cancelled: " + order);
            } else {
                System.out.println("Order cannot be canceled or does not exist.");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void showOrder(String orderId) {
        lock.readLock().lock();
        try {
            Order order = dbService.getOrder(orderId);
            if (order != null) {
                System.out.println("Order status: " + order);
            } else {
                System.out.println("Order not found.");
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public void showOrderBookForStock(String stockId) {
        lock.readLock().lock();
        try {
            PriorityQueue<Order> orderBook = dbService.getOrderBookForStockId(stockId);
            System.out.println("<---------- Order Book for stock Id: " + stockId + " --------------->");
            orderBook.forEach(System.out::println);
            System.out.println("<------------------------------------------------------------->");
        } finally {
            lock.readLock().unlock();
        }
    }

    public void expireOrders(int value, ChronoUnit chronoUnit) {
        lock.writeLock().lock();
        try {
            Map<String, PriorityQueue<Order>> orderBook = dbService.getOrderBook();
            orderBook.forEach((k, q) -> {
                List<Order> orders = new ArrayList<>();
                while (!q.isEmpty()) {
                    Order order = q.poll();
                    if (order.getAcceptedTimestamp().plus(value, chronoUnit).isAfter(Instant.now())) {
                        orders.add(order);
                    } else {
                        order.setStatus(OrderStatus.EXPIRED);
                        System.out.println("Order Expired: " + order);
                    }
                }
                q.addAll(orders);
            });

        } finally {
            lock.writeLock().unlock();
        }
    }

    private void matchOrders(Order order) {
        tradeMatchingStrategy.matchOrders(order);
    }


}
