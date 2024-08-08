package com.phonepe.services;

import com.phonepe.models.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class AppService {
    private final AccountService accountService;
    private final OrderService orderService;
    private final TradeService tradeService;

    public AppService(AccountService accountService, OrderService orderService, TradeService tradeService) {
        this.accountService = accountService;
        this.orderService = orderService;
        this.tradeService = tradeService;
    }

    public void placeOrder(Order order) {
        if (order.getOrderType().equals(OrderType.BUY)) {
            Account account = accountService.getAccount(order.getAccountId());
            if (account.getBalance() < (order.getQuantity() * order.getPrice())) {
                order.setStatus(OrderStatus.REJECTED);
                System.out.println("Insufficient funds for order: " + order);
                return;
            }
        }
        orderService.placeOrder(order);
    }

    public void modifyOrder(String orderId, int newQuantity, double newPrice) {
        orderService.modifyOrder(orderId, newQuantity, newPrice);
    }

    public void cancelOrder(String orderId) {
        orderService.cancelOrder(orderId);
    }

    public void showOrder(String orderId) {
        orderService.showOrder(orderId);
    }

    public void expireOrders(int value, ChronoUnit chronoUnit) {
        orderService.expireOrders(value, chronoUnit);
    }

    public List<Trade> getTradesForAccount(String accountId) {
        return tradeService.getTradesForAccount(accountId);
    }

    public void showOrderBookforStock(String stockId) {
        orderService.showOrderBookForStock(stockId);
    }
}
