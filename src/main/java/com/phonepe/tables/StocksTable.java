package com.phonepe.tables;

import com.phonepe.models.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StocksTable {
    List<Stock> stocks = new ArrayList<>();

    public boolean addStock(Stock Stock) {
        return stocks.add(Stock);
    }

    public Stock getStockById(String id) throws Exception {
        Optional<Stock> Stock = stocks.stream().filter(u -> u.getStockId().equals(id)).findFirst();
        if (Stock.isPresent()) {
            return Stock.get();
        } else {
            throw new Exception("Stock not found");
        }
    }
}
