package com.phonepe.tables;

import com.phonepe.models.Trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeTable {
    private final Map<String, Trade> trades = new HashMap<>();

    public Trade addTrade(Trade trade) {
        return trades.put(trade.getTradeId(), trade);
    }

    public Trade getTrade(String tradeId) {
        return trades.get(tradeId);
    }

    public List<Trade> getTradesForAccount(String accountId) {
        return new ArrayList<>();
    }

}
