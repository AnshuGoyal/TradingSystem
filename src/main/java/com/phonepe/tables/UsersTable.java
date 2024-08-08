package com.phonepe.tables;

import com.phonepe.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersTable {
    List<User> users = new ArrayList<>();

    public boolean addUser(User user) {
        return users.add(user);
    }

    public User getUserById(String id) throws Exception {
        Optional<User> user = users.stream().filter(u -> u.getUserId().equals(id)).findFirst();
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }

}
