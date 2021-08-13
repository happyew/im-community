package com.example.imcommunity.service.impl;

import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.repository.GiteeUserRepository;
import com.example.imcommunity.service.GiteeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiteeUserServiceImpl implements GiteeUserService {
    @Autowired
    private GiteeUserRepository giteeUserRepository;

    @Override
    public GiteeUser findByToken(String token) {
        return giteeUserRepository.findByToken(token);
    }
}
