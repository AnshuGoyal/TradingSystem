package com.phonepe.strategy;

import com.phonepe.models.Order;

public interface TradeMatchingStrategy {
    void matchOrders(Order order);
}
