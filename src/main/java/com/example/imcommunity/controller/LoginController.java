package com.example.imcommunity.controller;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.model.UserForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() || subject.isAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid UserForm userForm, String code, String rememberMe, Model model, HttpSession session) {
        CircleCaptcha captcha = (CircleCaptcha) session.getAttribute("captcha");
        if (captcha != null && !captcha.verify(code)) {
            session.removeAttribute("captcha");
            model.addAttribute("msg", "验证码错误");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() || subject.isAuthenticated()) {
            return "redirect:/";
        }
        String username = userForm.getUsername();
        String password = userForm.getPassword();
        if (StrUtil.hasEmpty(username) || StrUtil.hasBlank(username)) {
            model.addAttribute("msg", "用户名不能为空，不能有空格");
            return "login";
        }
        if (StrUtil.hasEmpty(password) || StrUtil.hasBlank(password)) {
            model.addAttribute("msg", "密码不能为空，不能有空格");
            return "login";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            if ("checked".equals(rememberMe)) {
                System.out.println("记住我");
                token.setRememberMe(true);
            }
            subject.login(token);
            return "redirect:/";
        } catch (UnknownAccountException e) {
//            e.printStackTrace();
            System.out.println("用户名错误");
            model.addAttribute("msg", "该用户不存在");
        } catch (IncorrectCredentialsException e) {
//            e.printStackTrace();
            System.out.println("密码错误");
            model.addAttribute("msg", "密码错误");
        }
        if (subject.isAuthenticated()) {
            System.out.println("认证成功");
            return "redirect:/";
        } else {
            token.clear();
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }
}
