package com.phonepe.models;

import java.time.Instant;

public class Order {
    private String orderId;
    private String accountId;
    private String stockId;
    private OrderType orderType;
    private OrderStatus status;
    private int quantity;
    private double price;
    private Instant acceptedTimestamp;

    public Order(String orderId, String accountId, String stockId, OrderType orderType, int quantity, double price, Instant acceptedTimestamp) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.stockId = stockId;
        this.orderType = orderType;
        this.status = OrderStatus.ACCEPTED;
        this.quantity = quantity;
        this.price = price;
        this.acceptedTimestamp = acceptedTimestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Instant getAcceptedTimestamp() {
        return acceptedTimestamp;
    }

    public void setAcceptedTimestamp(Instant acceptedTimestamp) {
        this.acceptedTimestamp = acceptedTimestamp;
    }

    @Override
    public String toString() {
        return "Order details -> {" +
                "orderId='" + orderId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", stockId='" + stockId + '\'' +
                ", orderType=" + orderType +
                ", status=" + status +
                ", quantity=" + quantity +
                ", price=" + price +
                ", acceptedTimestamp=" + acceptedTimestamp +
                '}';
    }
}

