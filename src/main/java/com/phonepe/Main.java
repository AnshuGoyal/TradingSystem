package com.phonepe;

import com.phonepe.admin.AdminService;
import com.phonepe.models.*;
import com.phonepe.services.*;
import com.phonepe.strategy.TradeMatchingStrategy;
import com.phonepe.strategy.TradeMatchingStrategy1;
import com.phonepe.utility.Functions;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        /* ------------------------------  Initializing required classes ------------------------------ */

        DbService dbService = new DbService();
        AccountService accountService = new AccountService(dbService);
        TradeService tradeService = new TradeService(dbService);
        TradeMatchingStrategy tradeMatchingStrategy = new TradeMatchingStrategy1(dbService, tradeService);
        OrderService orderService = new OrderService(dbService, tradeMatchingStrategy);
        AppService appService = new AppService(accountService, orderService, tradeService);

        UserService userService = new UserService(dbService);
        StockService stockService = new StockService(dbService);
        AdminService adminService = new AdminService(userService, stockService, accountService);

        /* ----------------------  Loading Dummy Data in System using Admin Service ------------------------ */

        User user1 = new User("U1", "Alice", "1234567890", "alice@example.com");
        User user2 = new User("U2", "Bob", "0987654321", "bob@example.com");
        adminService.addUser(user1);
        adminService.addUser(user2);
        Account account1 = new Account("ACC01", user1.getUserId(), "DMT01", 4000000d);
        Account account2 = new Account("ACC02", user1.getUserId(), "DMT02", 5000000d);
        adminService.addAccount(account1);
        adminService.addAccount(account2);
        Stock stock1 = new Stock("S01", "IDEA", "VODAFONE IDEA", "Telecom Stock");
        Stock stock2 = new Stock("S02", "VEDL", "VEDANTA LIMITED", "Metal Stock");
        adminService.addStock(stock1);
        adminService.addStock(stock2);


        /* ---------------------------  Expire Trades after a specific time ------------------------------ */

        // Batch is running at a freq of 1 sec and expiring orders older than 30 seconds
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable expiryTask = () -> appService.expireOrders(30, ChronoUnit.SECONDS);
        // Schedule the expiry task to run every minute
        scheduler.scheduleAtFixedRate(expiryTask, 0, 1, TimeUnit.SECONDS);


        /* ---------------------------  Creating 3 tasks for testing ------------------------------ */

        System.out.println(account1);
        System.out.println(account2);

        Runnable task1 = () -> {
            Order order1 = new Order("O1", account1.getAccountId(), stock1.getStockId(), OrderType.BUY,
                    10, 150.0, Instant.now());
            appService.placeOrder(order1);
//            appService.modifyOrder(order1.getOrderId(), 9, 140.0);
//            appService.cancelOrder(order1.getOrderId());
        };

        Runnable task2 = () -> {
            Order order2 = new Order("O2", account2.getAccountId(), stock2.getStockId(), OrderType.BUY,
                    20, 100.0, Instant.now());
            appService.placeOrder(order2);
//            appService.showOrder(order2.getOrderId());
//            appService.modifyOrder(order2.getOrderId(), 10, 80);
        };

        Runnable task3 = () -> {
            Order order3 = new Order("O3", account2.getAccountId(), stock1.getStockId(), OrderType.SELL,
                    15, 140.0, Instant.now());
            Order order4 = new Order("O4", account1.getAccountId(), stock2.getStockId(), OrderType.SELL,
                    15, 90.0, Instant.now());
            appService.placeOrder(order3);
            appService.placeOrder(order4);
        };

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        Thread thread3 = new Thread(task3);

        thread1.start();
        Functions.threadSleep(50);
        thread2.start();
        Functions.threadSleep(50);
        thread3.start();
        Functions.threadSleep(50);

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            appService.showOrderBookforStock(stock1.getStockId());
            appService.showOrderBookforStock(stock2.getStockId());

            System.out.println(account1);
            System.out.println(account2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
