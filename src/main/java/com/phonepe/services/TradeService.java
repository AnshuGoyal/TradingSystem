package com.phonepe.services;

import com.phonepe.models.Trade;

import java.util.List;

public class TradeService {
    private final DbService dbService;

    public TradeService(DbService dbService) {
        this.dbService = dbService;
    }
    public Trade addTrade(Trade trade) {
        return dbService.addTrade(trade);
    }
    public Trade getTrade(String tradeId) {
        return dbService.getTrade(tradeId);
    }

    public List<Trade> getTradesForAccount(String tradeId) {
        return dbService.getTradesForAccount(tradeId);
    }
}
