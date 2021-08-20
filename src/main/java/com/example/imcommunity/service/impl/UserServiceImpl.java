package com.example.imcommunity.service.impl;

import com.example.imcommunity.entity.Role;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.model.UserForm;
import com.example.imcommunity.repository.UserRepository;
import com.example.imcommunity.service.RoleService;
import com.example.imcommunity.service.UserService;
import com.example.imcommunity.util.PasswordUtil;
import com.example.imcommunity.util.SaltUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User create(User user) {
        User userExisted = findByUsername(user.getUsername());
        if (userExisted == null) {
            user.setGmtCreated(new Date());
            user.setGmtModified(user.getGmtCreated());
            user.setSalt(SaltUtil.getSalt());
            user.setPassword(PasswordUtil.toMd5Hash(user.getPassword(), user.getSalt()));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User create(UserForm userForm) {
        User userExisted = findByUsername(userForm.getUsername());
        if (userExisted == null) {
            Role userRole = roleService.findRoleByName("user");
            User newUser = new User();
            BeanUtils.copyProperties(userForm, newUser);
            newUser.setGmtCreated(new Date());
            newUser.setGmtModified(newUser.getGmtCreated());
            newUser.setSalt(SaltUtil.getSalt());
            newUser.setPassword(PasswordUtil.toMd5Hash(newUser.getPassword(), newUser.getSalt()));
            newUser.getRoles().add(userRole);
            return userRepository.save(newUser);
        }
        return null;
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }
}
