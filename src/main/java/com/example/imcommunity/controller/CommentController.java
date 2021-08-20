package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.entity.Comment;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.exception.CustomErrorCode;
import com.example.imcommunity.exception.CustomException;
import com.example.imcommunity.model.CommentForm;
import com.example.imcommunity.service.CommentService;
import com.example.imcommunity.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final NotificationService notificationService;

    public CommentController(CommentService commentService, NotificationService notificationService) {
        this.commentService = commentService;
        this.notificationService = notificationService;
    }

    @PostMapping("/comment/{id}")
    public String addComment(@PathVariable String id, CommentForm commentForm, HttpSession session) {
        if (StrUtil.hasEmpty(commentForm.getContent())) {
            return StrUtil.format("redirect:/question/{}", id);
        }
        Comment comment = commentService.create(commentForm);
        if (comment != null) {
            User notifier = (User) session.getAttribute("user");
            notificationService.create(comment, notifier);
            return StrUtil.format("redirect:/question/{}", id);
        }
        throw new CustomException(CustomErrorCode.BAD_REQUEST);
    }
}
