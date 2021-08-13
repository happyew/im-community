package com.example.imcommunity.controller;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/question/{id}")
    public String doGet(@PathVariable Long id, Model model) {
        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("questionDTO", questionDTO);
        return "question";
    }
}
