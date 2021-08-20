package com.example.imcommunity.controller;

import com.example.imcommunity.exception.CustomErrorCode;
import com.example.imcommunity.exception.CustomException;
import com.example.imcommunity.service.QuestionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/delete/question/{id}")
    public String deleteQuestion(@PathVariable Long id, @RequestParam("userId") String userId) {
        if (!SecurityUtils.getSubject().isPermitted("question:delete:" + userId)) {
            throw new CustomException(CustomErrorCode.ACCESS_DENIED);
        }
        questionService.deleteById(id);
        return "redirect:/";
    }
}
