package com.example.imcommunity.controller;

import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.service.GiteeUserService;
import com.example.imcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserProfileController {
    private final GiteeUserService giteeUserService;
    private final QuestionService questionService;

    public UserProfileController(GiteeUserService giteeUserService, QuestionService questionService) {
        this.giteeUserService = giteeUserService;
        this.questionService = questionService;
    }

    @GetMapping("/profile/question")
    public String profile(@RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    GiteeUser giteeUser = giteeUserService.findByToken(cookie.getValue());
                    if (giteeUser != null) {
                        QuestionPageDTO questionPageDTO = questionService.findPageByGiteeUser(page, size, giteeUser);
                        model.addAttribute("questionPageDTO", questionPageDTO);
                        request.getSession().setAttribute("user", giteeUser);
                        return "profile";
                    }
                }
            }
        }
        return "redirect:/";
    }
}
