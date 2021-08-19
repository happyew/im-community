package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.model.ReplyFrom;
import com.example.imcommunity.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @PostMapping("/comment")
    public String addReply(ReplyFrom replyFrom) {
        if (StrUtil.hasEmpty(replyFrom.getContent())) {
            return StrUtil.format("redirect:/question/{}", replyFrom.getQuestionId());
        }
        replyService.create(replyFrom);
        return StrUtil.format("redirect:/question/{}", replyFrom.getQuestionId());
    }
}
