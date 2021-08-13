package com.example.imcommunity.controller;

import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.repository.GiteeUserRepository;
import com.example.imcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 首页控制器
 */
@Controller
public class IndexController {
    private static final String PAGE = "1";
    private static final String SIZE = "5";
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
    public String index(@RequestParam(name = "page", defaultValue = PAGE) Integer page,
                        @RequestParam(name = "size", defaultValue = SIZE) Integer size,
                        HttpServletRequest request, HttpServletResponse response,
                        Model model) {
        if (page < 1) {
            page = Integer.getInteger(PAGE);
        }
        if (size < 1) {
            size = Integer.getInteger(SIZE);
        }
        QuestionPageDTO questionPageDTO = questionService.findAllPage(page, size);
        model.addAttribute("questionPageDTO", questionPageDTO);
        if (request.getSession().getAttribute("user") != null) {
            return "index";
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    GiteeUser giteeUser = giteeUserRepository.findByToken(cookie.getValue());
                    if (giteeUser != null) {
                        request.getSession().setAttribute("user", giteeUser);
                        return "index";
                    }
                }
            }
        }
        return "index";
    }
}
