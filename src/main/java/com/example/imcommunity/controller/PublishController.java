package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.dto.QuestionDTO;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.exception.CustomErrorCode;
import com.example.imcommunity.exception.CustomException;
import com.example.imcommunity.model.QuestionForm;
import com.example.imcommunity.service.QuestionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String publish() {
        return "publish";
    }

    /**
     * 新建问题
     *
     * @return 视图
     */
    @PostMapping("/publish")
    public String create(QuestionForm questionForm, Model model) {
        if (checkEmpty(questionForm, model)) return "publish";
        // 新建问题
        Question questionSaved = questionService.create(questionForm);
        return StrUtil.format("redirect:/question/{}", questionSaved.getId());
    }

    /**
     * 编辑问题
     *
     * @param id    问题id
     * @param model 模型
     * @return 视图
     */
    @GetMapping("/publish/{id}")
    public String update(@PathVariable Long id, Model model) {
        QuestionDTO questionDTO = questionService.findQuestionDTOById(id);
        Subject subject = SecurityUtils.getSubject();
        boolean permitted = subject.isPermitted("question:update:" + questionDTO.getUserid());
        if (!permitted) {
            throw new CustomException(CustomErrorCode.ACCESS_DENIED);
        }
        model.addAttribute("questionDTO", questionDTO);
        return "publish";
    }

    /**
     * 提交修改
     *
     * @param id           问题id
     * @param questionForm 问题表单
     * @param model        模型
     * @return 视图
     */
    @PostMapping("/publish/{id}")
    public String update(@PathVariable Long id, QuestionForm questionForm, Model model) {
        Subject subject = SecurityUtils.getSubject();
        boolean permitted = subject.isPermitted("question:update:" + questionForm.getUserId());
        if (!permitted) {
            throw new CustomException(CustomErrorCode.ACCESS_DENIED);
        }
        if (checkEmpty(questionForm, model)) return "publish";
        questionService.update(questionForm);
        return StrUtil.format("redirect:/question/{}", id);
    }

    private boolean checkEmpty(QuestionForm questionForm, Model model) {
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(questionForm, questionDTO);
        if (StrUtil.hasEmpty(questionForm.getTitle())) {
            model.addAttribute("questionDTO", questionDTO);
            model.addAttribute("msg", "标题不能为空");
            return true;
        }
        if (StrUtil.hasEmpty(questionForm.getDescription())) {
            model.addAttribute("questionDTO", questionDTO);
            model.addAttribute("msg", "标题不能为空");
            return true;
        }
/*        if (StrUtil.hasEmpty(questionForm.getTag())) {
            model.addAttribute("questionDTO", questionDTO);
            model.addAttribute("msg", "标签不能为空");
            return true;
        }*/
        return false;
    }
}
