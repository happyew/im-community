package com.example.imcommunity.controller;

import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.repository.GiteeUserRepository;
import com.example.imcommunity.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登陆后发起提问的控制器
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private GiteeUserRepository giteeUserRepository;

    /**
     * Get请求，登录后发起提问
     *
     * @param model   模型
     * @param request 请求
     * @return 视图
     */
    @GetMapping("/publish")
    public String publish(Model model, HttpServletRequest request) {
        // 根据cookie验证是否登录
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    GiteeUser giteeUser = giteeUserRepository.findByToken(cookie.getValue());
                    if (giteeUser != null) {
                        // 已登录
                        model.addAttribute("giteeUser", giteeUser);
                        model.addAttribute("isLogin", true);
                        return "publish";
                    }
                }
            }
        }
        // 未登录
        return "redirect:/";
    }

    /**
     * Post请求，提交问题
     *
     * @param title       标题
     * @param description 描述
     * @param tag         标签
     * @param request     请求
     * @return 视图
     */
    @PostMapping("/publish")
    public String save(@RequestParam(name = "title") String title,
                       @RequestParam(name = "description") String description,
                       @RequestParam(name = "tag") String tag,
                       HttpServletRequest request,
                       Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null | "".equals(title)) {
            model.addAttribute("error", "标题不能为空！");
            return "publish";
        }
        if (description == null | "".equals(description)) {
            model.addAttribute("error", "描述不能为空！");
            return "publish";
        }
        if (tag == null | "".equals(tag)) {
            model.addAttribute("error", "标签不能为空！");
            return "pubish";
        }
        Question question = new Question();
        question.setGmtCreate(new Date());
        question.setGmtModified(question.getGmtCreate());
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        // 验证是否登录
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    GiteeUser giteeUser = giteeUserRepository.findByToken(cookie.getValue());
                    if (giteeUser != null) {
                        // 已登录，保存问题到数据库
                        question.setGiteeUser(giteeUser);
                        questionRepository.save(question);
                        return "redirect:/";
                    }
                }
            }
        }
        // 未登录
        return "loginFailed";
    }
}
