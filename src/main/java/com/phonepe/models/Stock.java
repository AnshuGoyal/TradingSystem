package com.phonepe.models;

public class Stock {
    private String stockId;
    private String symbol;
    private String name;
    private String info;

    public Stock(String stockId, String symbol, String name, String info) {
        this.stockId = stockId;
        this.symbol = symbol;
        this.name = name;
        this.info = info;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
