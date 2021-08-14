package com.example.imcommunity.service.impl;

import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.repository.GiteeUserRepository;
import com.example.imcommunity.service.GiteeUserService;
import org.springframework.stereotype.Service;

@Service
public class GiteeUserServiceImpl implements GiteeUserService {
    private final GiteeUserRepository giteeUserRepository;

    public GiteeUserServiceImpl(GiteeUserRepository giteeUserRepository) {
        this.giteeUserRepository = giteeUserRepository;
    }

    @Override
    public GiteeUser findByToken(String token) {
        return giteeUserRepository.findByToken(token);
    }

    @Override
    public GiteeUser findByAccountId(String accountId) {
        return giteeUserRepository.findByAccountId(accountId);
    }

    @Override
    public GiteeUser save(GiteeUser giteeUser) {
        return giteeUserRepository.save(giteeUser);
    }
}
