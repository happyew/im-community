package com.example.imcommunity.controller;

import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;

/**
 * 问题控制器
 */
@Controller
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * 展示问题具体内容
     *
     * @param id    问题id
     * @param model 模型
     * @return 视图
     */
    @GetMapping("/question/{id}")
    public String show(@PathVariable Long id, Model model) {
        QuestionDTO questionDTO = questionService.findQuestionDTOById(id);
        model.addAttribute("questionDTO", questionDTO);
        return "question";
    }

    /**
     * 保存修改
     *
     * @param id          问题id
     * @param title       问题标题
     * @param description 问题描述
     * @param tag         问题标签
     * @param request     请求
     * @param modelMap    重定向模型
     * @return 重定向视图
     */
    @PostMapping("/question/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam(name = "title") String title,
                         @RequestParam(name = "description", defaultValue = "") String description,
                         @RequestParam(name = "tag", defaultValue = "") String tag,
                         HttpServletRequest request,
                         RedirectAttributesModelMap modelMap) {
        GiteeUser giteeUser = (GiteeUser) request.getSession().getAttribute("user");
        if (giteeUser != null) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setTitle(title);
            questionDTO.setDescription(description);
            questionDTO.setTag(tag);
            questionDTO.setId(id);
            if ("".equals(title)) {
                modelMap.addFlashAttribute("error", "标题不能为空！");
                modelMap.addFlashAttribute("questionDTO", questionDTO);
                return "redirect:/question/" + id + "/edit";
            }
            if ("".equals(description)) {
                modelMap.addFlashAttribute("error", "标题不能为空！");
                modelMap.addFlashAttribute("questionDTO", questionDTO);
                return "redirect:/question/" + id + "/edit";
            }
            if ("".equals(tag)) {
                modelMap.addFlashAttribute("error", "标题不能为空！");
                modelMap.addFlashAttribute("questionDTO", questionDTO);
                return "redirect:/question/" + id + "/edit";
            }
            Question question = questionService.findQuestionById(id);
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            questionService.save(question);
            return "redirect:/question/" + id;
        }
        return "redirect:/question/" + id;
    }

    /**
     * 编辑问题
     *
     * @param id       问题id
     * @param model    模型
     * @param request  请求
     * @param modelMap 重定向模型
     * @return 视图
     */
    @GetMapping("/question/{id}/edit")
    public String edit(@PathVariable Long id, Model model, HttpServletRequest request,
                       RedirectAttributesModelMap modelMap) {
        GiteeUser giteeUser = (GiteeUser) request.getSession().getAttribute("user");
        if (giteeUser != null) {
            if (modelMap.getFlashAttributes().size() != 0) {
                return "publish";
            }
            QuestionDTO questionDTO = questionService.findQuestionDTOById(id);
            model.addAttribute("questionDTO", questionDTO);
            return "publish";
        }
        return "redirect:/question/" + id;
    }
}
