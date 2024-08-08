package com.phonepe.strategy;

import com.phonepe.models.Order;

public interface OrderMatchingStrategy {
    void matchOrders(Order order);
}
