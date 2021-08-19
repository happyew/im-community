package com.example.imcommunity.controller;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.service.QuestionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 问题控制器
 */
@Controller
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * 展示问题具体内容
     *
     * @param id    问题id
     * @param model 模型
     * @return 视图
     */
    @GetMapping("/question/{id}")
    public String show(@PathVariable Long id,
                       @NotNull Model model) {
        // 增加浏览数
        questionService.viewIncrement(id);
        QuestionDTO questionDTO = questionService.findQuestionDTOById(id);
        model.addAttribute("questionDTO", questionDTO);
        return "question";
    }
}
