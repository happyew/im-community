package com.example.imcommunity.controller;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.service.QuestionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * @param request 请求
     * @return 视图
     */
    @GetMapping("/publish")
    public String publish(@NotNull HttpServletRequest request) {
        // 根据cookie验证是否登录
        GiteeUser user = (GiteeUser) request.getSession().getAttribute("user");
        if (user != null) {
            return "publish";
        }
        // 未登录
        return "redirect:/";
    }

    /**
     * 新建或修改问题
     *
     * @param title       问题标题
     * @param description 问题描述
     * @param tag         问题标签
     * @param request     请求
     * @return 视图
     */
    @PostMapping("/publish")
    public String save(@RequestParam(name = "title", defaultValue = "") String title,
                       @RequestParam(name = "description", defaultValue = "") String description,
                       @RequestParam(name = "tag", defaultValue = "") String tag,
                       @RequestParam(name = "id", defaultValue = "") Long id,
                       @NotNull HttpServletRequest request,
                       Model model) {
        // 验证是否登录
        GiteeUser giteeUser = (GiteeUser) request.getSession().getAttribute("user");
        if (giteeUser != null) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setTitle(title);
            questionDTO.setDescription(description);
            questionDTO.setTag(tag);
            // id不为空是修改问题
            if (id != null) {
                questionDTO.setId(id);
            }
            model.addAttribute("questionDTO", questionDTO);
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
            if (id != null) {
                // id不为空，更新问题
                Question question = questionService.findQuestionById(id);
                question.setTitle(title);
                question.setDescription(description);
                question.setTag(tag);
                // 更新修改时间
                question.setGmtModified(new Date());
                questionService.save(question);
                return "redirect:/question/" + id;
            }
            // id为空，新建问题
            Question question = new Question();
            question.setGmtCreate(new Date());
            question.setGmtModified(question.getGmtCreate());
            question.setTitle(title);
            question.setTag(tag);
            question.setDescription(description);
            question.setGiteeUser(giteeUser);
            Question questionSaved = questionService.save(question);
            return "redirect:/question/" + questionSaved.getId();
        }
        return "redirect:/";
    }

    /**
     * 编辑问题
     *
     * @param id      问题id
     * @param model   模型
     * @param request 请求
     * @return 视图
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable Long id,
                       @NotNull HttpServletRequest request,
                       Model model) {
        GiteeUser giteeUser = (GiteeUser) request.getSession().getAttribute("user");
        if (giteeUser != null) {
            QuestionDTO questionDTO = questionService.findQuestionDTOById(id);
            // 判断该问题的作者是否为当前登录用户
            if (giteeUser.getId().equals(questionDTO.getUserid())) {
                model.addAttribute("questionDTO", questionDTO);
                return "publish";
            }
        }
        return "redirect:/question/" + id;
    }
}
