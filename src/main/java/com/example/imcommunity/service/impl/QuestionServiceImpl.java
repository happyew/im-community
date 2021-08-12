package com.example.imcommunity.service.impl;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.repository.QuestionRepository;
import com.example.imcommunity.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<QuestionDTO> findAll() {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questions = questionRepository.findAll();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setAvatarUrl(question.getGiteeUser().getAvatarUrl());
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
