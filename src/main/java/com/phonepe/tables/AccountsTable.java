package com.phonepe.tables;

import com.phonepe.models.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountsTable {
    List<Account> accounts = new ArrayList<>();

    public boolean addAccount(Account account) {
        return accounts.add(account);
    }

    public Account getAccount(String accountId) {
        Optional<Account> account = accounts.stream().filter(u -> u.getAccountId().equals(accountId)).findFirst();
        return account.get();
    }

    public Account getAccountByUserId(String userId) throws Exception {
        Optional<Account> account = accounts.stream().filter(u -> u.getUserId().equals(userId)).findFirst();
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new Exception("Account not found");
        }
    }

    public void modifyAccountBalance(String accountId, double newBalance) {
        Account account = getAccount(accountId);
        account.setBalance(newBalance);
    }

}
