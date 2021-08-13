package com.example.imcommunity.controller;

import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登陆后发起提问的控制器
 */
@Controller
public class PublishController {
    private final QuestionService questionService;

    public PublishController(QuestionService questionService) {
        this.questionService = questionService;
    }

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
        GiteeUser user = (GiteeUser) request.getSession().getAttribute("user");
        if (user != null) {
            return "publish";
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
    public String save(@RequestParam(name = "title", defaultValue = "") String title,
                       @RequestParam(name = "description", defaultValue = "") String description,
                       @RequestParam(name = "tag", defaultValue = "") String tag,
                       HttpServletRequest request,
                       Model model) {
        // 验证是否登录
        GiteeUser giteeUser = (GiteeUser) request.getSession().getAttribute("user");
        if (giteeUser != null) {
            model.addAttribute("title", title);
            model.addAttribute("description", description);
            model.addAttribute("tag", tag);
            if ("".equals(title)) {
                model.addAttribute("error", "标题不能为空！");
                return "publish";
            }
            if ("".equals(description)) {
                model.addAttribute("error", "描述不能为空！");
                return "publish";
            }
            if ("".equals(tag)) {
                model.addAttribute("error", "标签不能为空！");
                return "publish";
            }
            Question question = new Question();
            question.setGmtCreate(new Date());
            question.setGmtModified(question.getGmtCreate());
            question.setTitle(title);
            question.setTag(tag);
            question.setDescription(description);
            question.setGiteeUser(giteeUser);
            questionService.save(question);
            return "redirect:/";
        }
        return "redirect:/";
    }
}
