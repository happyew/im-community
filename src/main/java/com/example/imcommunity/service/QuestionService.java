package com.example.imcommunity.service;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.entity.Question;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> findAll();

    QuestionPageDTO findAllPage(Integer page, Integer size, Sort sort);

    QuestionPageDTO findPageByGiteeUser(Integer page, Integer size, Sort sort, GiteeUser giteeUser);

    Question save(Question question);

    QuestionDTO findQuestionDTOById(Long id);

    Question findQuestionById(Long id);
}
