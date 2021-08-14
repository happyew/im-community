package com.example.imcommunity.service.impl;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.repository.QuestionRepository;
import com.example.imcommunity.service.QuestionService;
import com.example.imcommunity.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<QuestionDTO> findAll() {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questions = questionRepository.findAll();
        questions.forEach(question -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setAvatarUrl(question.getGiteeUser().getAvatarUrl());
            questionDTOS.add(questionDTO);
        });
        return questionDTOS;
    }

    @Override
    public QuestionPageDTO findAllPage(Integer page, Integer size, Sort sort) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "gmtCreate");
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

    @Override
    public QuestionPageDTO findPageByGiteeUser(Integer page, Integer size, Sort sort, GiteeUser giteeUser) {
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Question> questions = questionRepository.findByGiteeUser(giteeUser, pageable);
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
        }
        return null;
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public QuestionDTO findQuestionDTOById(Long id) {
        Optional<Question> optional = questionRepository.findById(id);
        if (optional.isPresent()) {
            Question question = optional.get();
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setAvatarUrl(question.getGiteeUser().getAvatarUrl());
            questionDTO.setUsername(question.getGiteeUser().getName());
            questionDTO.setUserid(question.getGiteeUser().getId());
            return questionDTO;
        }
        return null;
    }

    @Override
    public Question findQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }
}
