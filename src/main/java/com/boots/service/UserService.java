package com.boots.service;

import com.boots.model.User;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    void addUser(User user, String[] roles);

    User getUserById(Long id, User user, String[] roles);
    void updateUser(User user, String[] roles);

    void removeUserById(Long id);
    List<User> listUsers();
}