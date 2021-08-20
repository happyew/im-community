package com.example.imcommunity.controller;

import cn.hutool.captcha.CircleCaptcha;
import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.model.SetPasswordForm;
import com.example.imcommunity.service.QuestionService;
import com.example.imcommunity.service.UserService;
import com.example.imcommunity.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 用户个人资料控制器
 */
@Controller
public class ProfileController {
    @Autowired
    private UserService userService;
    private final QuestionService questionService;

    public ProfileController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * 显示用户本人的所有提问
     *
     * @param page  页数
     * @param size  单页元素长度
     * @param model 模型
     * @return 视图
     */
    @GetMapping("/profile/question")
    public String question(@RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "size", defaultValue = "5") Integer size,
                           HttpSession session,
                           Model model) {
        // 按修改时间倒序显示
        User user = (User) session.getAttribute("user");
        Sort sort = Sort.by(Sort.Direction.DESC, "gmtModified");
        QuestionPageDTO questionPageDTO = questionService.findPageByUser(page, size, sort, user);
        model.addAttribute("questionPageDTO", questionPageDTO);
        return "profile";
    }

    @GetMapping("/profile/setPassword")
    public String setPassword() {
        return "profile";
    }

    @PostMapping("/profile/setPassword")
    public String setPassword(SetPasswordForm setPasswordForm, String code, HttpSession session, Model model) {
        CircleCaptcha captcha = (CircleCaptcha) session.getAttribute("captcha");
        if (captcha != null && !captcha.verify(code)) {
            session.removeAttribute("captcha");
            model.addAttribute("msg", "验证码错误");
            return "profile";
        }
        if (!setPasswordForm.getPassword().equals(setPasswordForm.getPasswordRepeated())) {
            model.addAttribute("msg", "两次填写的新密码不一致");
            return "profile";
        }
        User user = (User) session.getAttribute("user");
        String passwordOldMd5 = PasswordUtil.toMd5Hash(setPasswordForm.getPasswordOld(), user.getSalt());
        if (!passwordOldMd5.equals(user.getPassword())) {
            model.addAttribute("msg", "旧密码错误");
            return "profile";
        }
        userService.update(setPasswordForm, user);
        return "redirect:/logout";
    }
}
