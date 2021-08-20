package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.model.CommentForm;
import com.example.imcommunity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/question/{id}/comment")
    public String addComment(@PathVariable String id, CommentForm commentForm) {
        if (StrUtil.hasEmpty(commentForm.getContent())) {
            return StrUtil.format("redirect:/question/{}", id);
        }
        if (commentService.create(commentForm) != null) {
            return StrUtil.format("redirect:/question/{}", id);
        }
        return null;
    }
}
