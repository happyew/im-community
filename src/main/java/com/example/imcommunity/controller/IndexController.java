package com.example.imcommunity.controller;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.repository.GiteeUserRepository;
import com.example.imcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页控制器
 */
@Controller
public class IndexController {
    @Autowired
    private GiteeUserRepository giteeUserRepository;
    @Autowired
    private QuestionService questionService;

    /**
     * 首页
     *
     * @param request 请求
     * @param model   模型
     * @return 视图
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        List<QuestionDTO> questionDTOS = questionService.findAll();
        model.addAttribute("questionDTOS", questionDTOS);
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    GiteeUser giteeUser = giteeUserRepository.findByToken(cookie.getValue());
                    if (giteeUser != null) {
                        model.addAttribute("giteeUser", giteeUser);
                        model.addAttribute("isLogin", true);
                        return "index";
                    }
                }
            }
        }
        model.addAttribute("isLogin", false);
        return "index";
    }
}
