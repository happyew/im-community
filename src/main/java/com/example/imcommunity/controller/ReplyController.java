package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.model.ReplyForm;
import com.example.imcommunity.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @PostMapping("/comment")
    public String addReply(ReplyForm replyForm) {
        if (StrUtil.hasEmpty(replyForm.getContent())) {
            return StrUtil.format("redirect:/question/{}", replyForm.getQuestionId());
        }
        replyService.create(replyForm);
        return StrUtil.format("redirect:/question/{}", replyForm.getQuestionId());
    }
}
