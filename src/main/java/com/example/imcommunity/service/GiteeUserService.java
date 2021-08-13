package com.example.imcommunity.service;

import com.example.imcommunity.entity.GiteeUser;

public interface GiteeUserService {
    GiteeUser findByToken(String token);
}
