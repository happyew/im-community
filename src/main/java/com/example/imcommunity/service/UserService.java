package com.example.imcommunity.service;

import com.example.imcommunity.entity.User;
import com.example.imcommunity.model.UserFrom;

public interface UserService {
    User findByUsername(String username);

    User create(User user);

    User create(UserFrom userFrom);

    User findUserById(Long id);
}
