package com.phonepe.services;

import com.phonepe.models.Account;

public class AccountService {
    private final DbService dbService;

    public AccountService(DbService dbService) {
        this.dbService = dbService;
    }

    public Boolean addAccount(Account account) {
        return dbService.addAccount(account);
    }

    public Account getAccount(String accountId) {
        return dbService.getAccount(accountId);
    }
}
