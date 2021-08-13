package com.example.imcommunity.service.impl;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.repository.QuestionRepository;
import com.example.imcommunity.service.QuestionService;
import com.example.imcommunity.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setAvatarUrl(question.getGiteeUser().getAvatarUrl());
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    @Override
    public QuestionPageDTO findPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Question> questions = questionRepository.findAll(pageable);
        if (questions.getContent().size() > 0) {
            PageUtil.PageDetail pageDetail = PageUtil.getPageDetail(questions, page);
            QuestionPageDTO questionPageDTO = new QuestionPageDTO();
            List<QuestionDTO> questionDTOList = questionPageDTO.getQuestionDTOList();
            BeanUtils.copyProperties(pageDetail, questionPageDTO);
            questions.getContent().forEach(question -> {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setAvatarUrl(question.getGiteeUser().getAvatarUrl());
                questionDTOList.add(questionDTO);
            });
            return questionPageDTO;
        } else {
            return null;
        }

    }
}
