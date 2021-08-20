package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.entity.Reply;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.model.ReplyForm;
import com.example.imcommunity.service.NotificationService;
import com.example.imcommunity.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final NotificationService notificationService;

    public ReplyController(ReplyService replyService, NotificationService notificationService) {
        this.replyService = replyService;
        this.notificationService = notificationService;
    }

    @PostMapping("/reply")
    public String addReply(ReplyForm replyForm, HttpSession session) {
        if (StrUtil.hasEmpty(replyForm.getContent())) {
            return StrUtil.format("redirect:/question/{}", replyForm.getQuestionId());
        }
        Reply reply = replyService.create(replyForm);
        User user = (User) session.getAttribute("user");
        notificationService.create(reply, user);
        return StrUtil.format("redirect:/question/{}", replyForm.getQuestionId());
    }
}
