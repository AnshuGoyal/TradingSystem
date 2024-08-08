package com.phonepe.admin;

import com.phonepe.models.Account;
import com.phonepe.models.Stock;
import com.phonepe.models.User;
import com.phonepe.services.AccountService;
import com.phonepe.services.StockService;
import com.phonepe.services.UserService;


// Admin Service is used for user and stocks management
public class AdminService {
    UserService userService;
    StockService stockService;
    AccountService accountService;

    public AdminService(UserService userService, StockService stockService, AccountService accountService) {
        this.userService = userService;
        this.stockService = stockService;
        this.accountService = accountService;
    }

    public boolean addUser(User user) {
        return userService.addUser(user);
    }

    public boolean addAccount(Account account) {
        return accountService.addAccount(account);
    }

    public boolean addStock(Stock stock) {
        return stockService.addStock(stock);
    }
}
