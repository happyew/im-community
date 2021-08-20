package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.exception.CustomErrorCode;
import com.example.imcommunity.exception.CustomException;
import com.example.imcommunity.service.CommentService;
import com.example.imcommunity.service.QuestionService;
import com.example.imcommunity.service.ReplyService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteController {
    private final QuestionService questionService;
    private final CommentService commentService;
    private final ReplyService replyService;

    public DeleteController(QuestionService questionService, CommentService commentService, ReplyService replyService) {
        this.questionService = questionService;
        this.commentService = commentService;
        this.replyService = replyService;
    }

    @GetMapping("/delete/question/{id}")
    public String deleteQuestion(@PathVariable Long id, @RequestParam("userId") String userId) {
        if (!SecurityUtils.getSubject().isPermitted("question:delete:" + userId)) {
            throw new CustomException(CustomErrorCode.ACCESS_DENIED);
        }
        questionService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/delete/comment/{id}")
    public String deleteComment(@PathVariable Long id,
                                @RequestParam("userId") String userId,
                                @RequestParam("questionId") String questionId) {
        if (!SecurityUtils.getSubject().isPermitted("question:delete:" + userId)) {
            throw new CustomException(CustomErrorCode.ACCESS_DENIED);
        }
        commentService.deleteById(id);
        return StrUtil.format("redirect:/question/{}", questionId);
    }

    @GetMapping("/delete/reply/{id}")
    public String deleteReply(@PathVariable Long id,
                              @RequestParam("userId") String userId,
                              @RequestParam("questionId") String questionId) {
        if (!SecurityUtils.getSubject().isPermitted("question:delete:" + userId)) {
            throw new CustomException(CustomErrorCode.ACCESS_DENIED);
        }
        replyService.deleteById(id);
        return StrUtil.format("redirect:/question/{}", questionId);
    }
}
