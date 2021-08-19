package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.model.QuestionFrom;
import com.example.imcommunity.service.QuestionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
     * @return 视图
     */
    @GetMapping("/publish")
    public String publish(HttpSession session) {
        return "publish";
    }

    /**
     * 新建或修改问题
     *
     * @return 视图
     */
    @PostMapping("/publish")
    public String save(QuestionFrom questionFrom, Long id,
                       HttpSession session,
                       Model model) {
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(questionFrom, questionDTO);
        // id不为空是修改问题
        if (id != null) {
            questionDTO.setId(id);
        }
        model.addAttribute("questionDTO", questionDTO);
        if ("".equals(questionDTO.getTitle())) {
            model.addAttribute("error", "标题不能为空！");
            return "publish";
        }
        if ("".equals(questionDTO.getDescription())) {
            model.addAttribute("error", "描述不能为空！");
            return "publish";
        }
        if ("".equals(questionDTO.getTag())) {
            model.addAttribute("error", "标签不能为空！");
            return "publish";
        }
        if (id != null) {
            // id不为空，更新问题
            questionFrom.setId(id);
            questionService.update(questionFrom);
            return StrUtil.format("redirect:/question/{}", id);
        }
        // id为空，新建问题
        Question questionSaved = questionService.create(questionFrom);
        return StrUtil.format("redirect:/question/{}", questionSaved.getId());
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
        QuestionDTO questionDTO = questionService.findQuestionDTOById(id);

        model.addAttribute("questionDTO", questionDTO);
        return "publish";
    }
}
