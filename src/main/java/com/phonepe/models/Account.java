package com.phonepe.models;

public class Account {
    private String accountId;
    private String userId;
    private String dematNumber;
    private Double balance;

    public Account(String accountId, String userId, String dematNumber, Double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.dematNumber = dematNumber;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDematNumber() {
        return dematNumber;
    }

    public void setDematNumber(String dematNumber) {
        this.dematNumber = dematNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account details: {" +
                "accountId='" + accountId + '\'' +
                ", userId='" + userId + '\'' +
                ", dematNumber='" + dematNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
