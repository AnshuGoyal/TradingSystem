package com.phonepe.models;

import java.time.Instant;

public class Trade {
    private String tradeId;
    private OrderType tradeType;
    private String buyerOrderId;
    private String sellerOrderId;
    private String stockId;
    private int quantity;
    private double price;
    private Instant tradeTimestamp;

    public Trade(String tradeId, OrderType tradeType, String buyerOrderId, String sellerOrderId, String stockId,
                 int quantity, double price, Instant tradeTimestamp) {
        this.tradeId = tradeId;
        this.tradeType = tradeType;
        this.buyerOrderId = buyerOrderId;
        this.sellerOrderId = sellerOrderId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.price = price;
        this.tradeTimestamp = tradeTimestamp;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public OrderType getTradeType() {
        return tradeType;
    }

    public void setTradeType(OrderType tradeType) {
        this.tradeType = tradeType;
    }

    public String getBuyerOrderId() {
        return buyerOrderId;
    }

    public void setBuyerOrderId(String buyerOrderId) {
        this.buyerOrderId = buyerOrderId;
    }

    public String getSellerOrderId() {
        return sellerOrderId;
    }

    public void setSellerOrderId(String sellerOrderId) {
        this.sellerOrderId = sellerOrderId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
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

    public Instant getTradeTimestamp() {
        return tradeTimestamp;
    }

    public void setTradeTimestamp(Instant tradeTimestamp) {
        this.tradeTimestamp = tradeTimestamp;
    }

    @Override
    public String toString() {
        return "Trade details -> {" +
                "tradeId='" + tradeId + '\'' +
                ", tradeType=" + tradeType +
                ", buyerOrderId='" + buyerOrderId + '\'' +
                ", sellerOrderId='" + sellerOrderId + '\'' +
                ", stockId='" + stockId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", tradeTimestamp=" + tradeTimestamp +
                '}';
    }
}
