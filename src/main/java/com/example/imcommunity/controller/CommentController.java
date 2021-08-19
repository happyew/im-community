package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.model.CommentFrom;
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
    public String addComment(@PathVariable String id, CommentFrom commentFrom) {
        if (StrUtil.hasEmpty(commentFrom.getContent())) {
            return StrUtil.format("redirect:/question/{}", id);
        }
        if (commentService.create(commentFrom) != null) {
            return StrUtil.format("redirect:/question/{}", id);
        }
        return null;
    }
}
