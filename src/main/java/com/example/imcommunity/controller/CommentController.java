package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.exception.CustomErrorCode;
import com.example.imcommunity.exception.CustomException;
import com.example.imcommunity.model.CommentForm;
import com.example.imcommunity.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/{id}")
    public String addComment(@PathVariable String id, CommentForm commentForm) {
        if (StrUtil.hasEmpty(commentForm.getContent())) {
            return StrUtil.format("redirect:/question/{}", id);
        }
        if (commentService.create(commentForm) != null) {
            return StrUtil.format("redirect:/question/{}", id);
        }
        throw new CustomException(CustomErrorCode.BAD_REQUEST);
    }
}
