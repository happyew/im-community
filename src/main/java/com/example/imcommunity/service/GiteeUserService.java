package com.example.imcommunity.service;

import com.example.imcommunity.entity.GiteeUser;

/**
 * giteeUser业务层接口
 */
public interface GiteeUserService {
    /**
     * 根据token在数据库中查找Gitee用户
     *
     * @param token token
     * @return Gitee用户
     */
    GiteeUser findByToken(String token);
}
