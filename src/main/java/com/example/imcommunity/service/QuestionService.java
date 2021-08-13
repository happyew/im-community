package com.example.imcommunity.service;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.entity.GiteeUser;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> findAll();

    QuestionPageDTO findAllPage(Integer page, Integer size);

    QuestionPageDTO findPageByGiteeUser(Integer page, Integer size, GiteeUser giteeUser);

}
