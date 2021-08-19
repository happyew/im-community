package com.example.imcommunity.service.impl;

import com.example.imcommunity.entity.User;
import com.example.imcommunity.model.UserFrom;
import com.example.imcommunity.repository.UserRepository;
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

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User create(UserFrom userFrom) {
        User userExisted = findByUsername(userFrom.getUsername());
        if (userExisted == null) {
            User user = new User();
            BeanUtils.copyProperties(userFrom, user);
            user.setGmtCreated(new Date());
            user.setGmtModified(user.getGmtCreated());
            user.setSalt(SaltUtil.getSalt());
            user.setPassword(PasswordUtil.toMd5Hash(user.getPassword(), user.getSalt()));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        return null;
    }
}
