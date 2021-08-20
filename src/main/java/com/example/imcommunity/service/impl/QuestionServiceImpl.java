package com.example.imcommunity.service.impl;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.exception.CustomErrorCode;
import com.example.imcommunity.exception.CustomException;
import com.example.imcommunity.model.QuestionForm;
import com.example.imcommunity.repository.QuestionRepository;
import com.example.imcommunity.service.QuestionService;
import com.example.imcommunity.service.UserService;
import com.example.imcommunity.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final UserService userService;

    public QuestionServiceImpl(QuestionRepository questionRepository, UserService userService) {
        this.questionRepository = questionRepository;
        this.userService = userService;
    }

    @Override
    public List<QuestionDTO> findAllQuestionDTO() {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questions = questionRepository.findAll();
        if (questions.size() > 0) {
            questions.forEach(question -> {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setAvatarUrl(question.getUser().getAvatarUrl());
                questionDTOS.add(questionDTO);
            });
            return questionDTOS;
        }
        return null;
    }

    @Override
    public QuestionPageDTO findAllPage(Integer page, Integer size, Sort sort) {
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Question> questions = questionRepository.findAll(pageable);
        if (questions.getContent().size() > 0) {
            PageUtil.PageDetail pageDetail = PageUtil.getPageDetail(questions, page);
            QuestionPageDTO questionPageDTO = new QuestionPageDTO();
            List<QuestionDTO> questionDTOList = questionPageDTO.getQuestionDTOList();
            BeanUtils.copyProperties(pageDetail, questionPageDTO);
            questions.getContent().forEach(question -> {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setAvatarUrl(question.getUser().getAvatarUrl());
                questionDTOList.add(questionDTO);
            });
            return questionPageDTO;
        }
        return null;
    }

    @Override
    public QuestionPageDTO findPageByUser(Integer page, Integer size, Sort sort, User user) {
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Question> questions = questionRepository.findByUser(user, pageable);
        if (questions.getContent().size() > 0) {
            PageUtil.PageDetail pageDetail = PageUtil.getPageDetail(questions, page);
            QuestionPageDTO questionPageDTO = new QuestionPageDTO();
            List<QuestionDTO> questionDTOList = questionPageDTO.getQuestionDTOList();
            BeanUtils.copyProperties(pageDetail, questionPageDTO);
            questions.getContent().forEach(question -> {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setAvatarUrl(question.getUser().getAvatarUrl());
                questionDTOList.add(questionDTO);
            });
            return questionPageDTO;
        }
        return null;
    }

    private Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public QuestionDTO findQuestionDTOById(Long id) {
        Question question = findQuestionById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setAvatarUrl(question.getUser().getAvatarUrl());
        questionDTO.setUsername(question.getUser().getUsername());
        questionDTO.setUserid(question.getUser().getId());
        questionDTO.setComments(question.getComments());
        return questionDTO;
    }

    @Override
    public Question findQuestionById(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            return optionalQuestion.get();
        }
        throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
    }

    @Override
    public void viewIncrement(Long id) {
        Question question = findQuestionById(id);
        question.setViewCount(question.getViewCount() + 1);
        save(question);
    }

    @Override
    public Question update(QuestionForm questionForm) {
        Question question = findQuestionById(questionForm.getId());
        BeanUtils.copyProperties(questionForm, question);
        question.setGmtModified(new Date());
        return save(question);
    }

    @Override
    public Question create(QuestionForm questionForm) {
        Question newQuestion = new Question();
        BeanUtils.copyProperties(questionForm, newQuestion);
        newQuestion.setUser(userService.findUserById(questionForm.getUserId()));
        newQuestion.setGmtCreated(new Date());
        newQuestion.setGmtModified(newQuestion.getGmtCreated());
        return save(newQuestion);
    }
}
