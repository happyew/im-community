package com.example.imcommunity.service;

import com.example.imcommunity.dto.QuestionDTO;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> findAll();
}
