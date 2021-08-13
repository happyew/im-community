package com.example.imcommunity.service;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.dto.QuestionPageDTO;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> findAll();
    QuestionPageDTO findPage(Integer page, Integer size);
}
