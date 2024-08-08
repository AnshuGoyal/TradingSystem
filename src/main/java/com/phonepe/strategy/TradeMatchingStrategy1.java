package com.phonepe.strategy;

import com.phonepe.models.Account;
import com.phonepe.models.Order;
import com.phonepe.models.OrderType;
import com.phonepe.models.Trade;
import com.phonepe.services.DbService;
import com.phonepe.services.TradeService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TradeMatchingStrategy1 implements TradeMatchingStrategy {
    private final DbService dbService;
    private final TradeService tradeService;

    public TradeMatchingStrategy1(DbService dbService, TradeService tradeService) {
        this.dbService = dbService;
        this.tradeService = tradeService;
    }

    @Override
    public void matchOrders(Order order) {
        PriorityQueue<Order> orderBook = dbService.getOrderBookForStockId(order.getStockId());
        if (orderBook == null || orderBook.isEmpty()) {
            return;
        }

        PriorityQueue<Order> buyOrderBook = new PriorityQueue<>(Comparator.comparing(Order::getAcceptedTimestamp));
        PriorityQueue<Order> sellOrderBook = new PriorityQueue<>(Comparator.comparing(Order::getAcceptedTimestamp));
        while (!orderBook.isEmpty()) {
            Order o = orderBook.poll();
            if (o.getOrderType().equals(OrderType.BUY)) buyOrderBook.add(o);
            else sellOrderBook.add(o);
        }

        // Match orders by ordertype and extract executed trades
        List<Trade> trades;
        if (order.getOrderType().equals(OrderType.BUY)) {
            trades = matchBuyOrder(order, buyOrderBook, orderBook, sellOrderBook);
        } else {
            trades = matchSellOrder(order, sellOrderBook, orderBook, buyOrderBook);
        }

        updateAccountBalances(trades);

    }

    private void updateAccountBalances(List<Trade> trades) {
        for (Trade trade : trades) {
            System.out.println("Trade executed: " + trade);

            // Modify Account balances
            try {
                double totalAmount = trade.getPrice() * trade.getQuantity();
                Order buyerOrder = dbService.getOrder(trade.getBuyerOrderId());
                Order sellerOrder = dbService.getOrder(trade.getSellerOrderId());
                Account buyerAccount = dbService.getAccount(buyerOrder.getAccountId());
                Account sellerAccount = dbService.getAccount(sellerOrder.getAccountId());
                dbService.modifyAccountBalance(buyerAccount.getAccountId(), buyerAccount.getBalance() - totalAmount);
                dbService.modifyAccountBalance(sellerAccount.getAccountId(), sellerAccount.getBalance() + totalAmount);
            } catch (Exception e) {
                System.out.println("Account settlement failed. ");
            }
        }
    }

    private List<Trade> matchSellOrder(Order order, PriorityQueue<Order> sellOrderBook, PriorityQueue<Order> orderBook,
                                       PriorityQueue<Order> buyOrderBook) {
        List<Trade> trades = new ArrayList<>();

        sellOrderBook.remove(order);
        orderBook.addAll(sellOrderBook);
        while (!buyOrderBook.isEmpty()) {
            Order buyOrder = buyOrderBook.poll();
            if (order.getPrice() <= buyOrder.getPrice()) {

                int quantity = Math.min(order.getQuantity(), buyOrder.getQuantity());
                Trade trade = new Trade(
                        "T" + System.currentTimeMillis(), // Simple trade ID
                        OrderType.SELL,
                        buyOrder.getOrderId(),
                        order.getOrderId(),
                        order.getStockId(),
                        quantity,
                        order.getPrice(),
                        Instant.now()
                );
                trades.add(trade);
                tradeService.addTrade(trade);

                order.setQuantity(order.getQuantity() - quantity);
                buyOrder.setQuantity(buyOrder.getQuantity() - quantity);
                if (buyOrder.getQuantity() > 0) {
                    buyOrderBook.add(buyOrder);
                }
                if (order.getQuantity() <= 0) {
                    orderBook.addAll(buyOrderBook);
                    break;
                }
            } else {
                orderBook.add(buyOrder);
            }
        }
        if (order.getQuantity() > 0) orderBook.add(order);
        return trades;
    }

    private List<Trade> matchBuyOrder(Order order, PriorityQueue<Order> buyOrderBook, PriorityQueue<Order> orderBook,
                                      PriorityQueue<Order> sellOrderBook) {

        List<Trade> trades = new ArrayList<>();

        buyOrderBook.remove(order);
        orderBook.addAll(buyOrderBook);
        while (!sellOrderBook.isEmpty()) {
            Order sellOrder = sellOrderBook.poll();
            if (order.getPrice() >= sellOrder.getPrice()) {

                int quantity = Math.min(order.getQuantity(), sellOrder.getQuantity());
                Trade trade = new Trade(
                        "T" + System.currentTimeMillis(), // Simple trade ID
                        OrderType.BUY,
                        order.getOrderId(),
                        sellOrder.getOrderId(),
                        order.getStockId(),
                        quantity,
                        sellOrder.getPrice(),
                        Instant.now()
                );
                trades.add(trade);
                tradeService.addTrade(trade);

                order.setQuantity(order.getQuantity() - quantity);
                sellOrder.setQuantity(sellOrder.getQuantity() - quantity);
                if (sellOrder.getQuantity() > 0) {
                    sellOrderBook.add(sellOrder);
                }
                if (order.getQuantity() <= 0) {
                    orderBook.addAll(sellOrderBook);
                    break;
                }
            } else {
                orderBook.add(sellOrder);
            }
        }
        if (order.getQuantity() > 0) orderBook.add(order);
        return trades;
    }

}
