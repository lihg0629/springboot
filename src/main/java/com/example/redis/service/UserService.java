package com.example.redis.service;

import com.example.redis.bean.User;

public interface UserService {

    User findUserByName(String userName);
    User findUserById(Long id);
    User saveUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
}
