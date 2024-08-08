package com.phonepe.services;

import com.phonepe.models.Stock;

public class StockService {
    private final DbService dbService;

    public StockService(DbService dbService) {
        this.dbService = dbService;
    }

    public Boolean addStock(Stock stock) {
        return dbService.addStock(stock);
    }
}
