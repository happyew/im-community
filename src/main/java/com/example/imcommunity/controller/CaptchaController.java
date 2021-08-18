package com.example.imcommunity.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class CaptchaController {
    @GetMapping("/captcha/**")
    public void captcha(HttpSession session, HttpServletResponse response) {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            session.setAttribute("captcha", captcha);
            response.setContentType("image/png");
            captcha.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
