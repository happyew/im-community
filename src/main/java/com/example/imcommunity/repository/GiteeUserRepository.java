package com.example.imcommunity.repository;

import com.example.imcommunity.entity.GiteeUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Gitee用户实体类JPA接口
 */
public interface GiteeUserRepository extends JpaRepository<GiteeUser, Long> {
    /**
     * 根据token查询用户
     *
     * @param token 保存用户数据时生产的唯一UUID
     * @return Gitee用户实体类
     */
    GiteeUser findByToken(String token);

    /**
     * 根据Gitee账号ID查询用户
     *
     * @param accountId Gitee账号ID
     * @return Gitee用户实体类
     */
    GiteeUser findByAccountId(String accountId);
}