package com.phonepe.services;

import com.phonepe.models.User;

public class UserService {
    private final DbService dbService;

    public UserService(DbService dbService) {
        this.dbService = dbService;
    }

    public Boolean addUser(User user) {
        return dbService.addUser(user);
    }
}
