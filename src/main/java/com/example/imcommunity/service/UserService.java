package com.example.imcommunity.service;

import com.example.imcommunity.entity.User;
import com.example.imcommunity.model.SetPasswordForm;
import com.example.imcommunity.model.UserForm;

public interface UserService {
    User findByUsername(String username);

    User create(User user);

    User create(UserForm userForm);

    User findUserById(Long id);

    void update(SetPasswordForm setPasswordForm, User user);
}
