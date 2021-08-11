package com.example.imcommunity.controller;

import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.repository.GiteeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 首页控制器
 */
@Controller
public class IndexController {
    @Autowired
    private GiteeUserRepository giteeUserRepository;

    /**
     * 首页
     *
     * @param request 请求
     * @param model   模型
     * @return 视图
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    GiteeUser giteeUser = giteeUserRepository.findByToken(cookie.getValue());
                    if (giteeUser != null) {
                        model.addAttribute("user", giteeUser);
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
