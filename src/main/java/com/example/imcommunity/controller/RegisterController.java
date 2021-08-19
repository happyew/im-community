package com.example.imcommunity.controller;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.model.UserFrom;
import com.example.imcommunity.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(UserFrom userFrom, String code, Model model, HttpSession session) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/";
        }
        CircleCaptcha captcha = (CircleCaptcha) session.getAttribute("captcha");
        if (captcha == null || !captcha.verify(code)) {
            session.removeAttribute("captcha");
            model.addAttribute("msg", "验证码错误");
            return "register";
        }
        String username = userFrom.getUsername();
        String password = userFrom.getPassword();
        if (StrUtil.hasEmpty(username) || StrUtil.containsBlank(username)) {
            model.addAttribute("msg", "用户名不能有空格");
            return "register";
        }
        if (StrUtil.hasEmpty(password) || StrUtil.containsBlank(password)) {
            model.addAttribute("msg", "密码不能有空格");
            return "register";
        }
        if (userService.create(userFrom) == null) {
            model.addAttribute("msg", "该用户名已被使用");
            return "register";
        }
        model.addAttribute("msg","注册成功!");
        return "registerSuccess";
    }
}
