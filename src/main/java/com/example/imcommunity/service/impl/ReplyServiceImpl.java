package com.example.imcommunity.service.impl;

import com.example.imcommunity.entity.Comment;
import com.example.imcommunity.entity.Reply;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.model.ReplyForm;
import com.example.imcommunity.repository.ReplyRepository;
import com.example.imcommunity.service.CommentService;
import com.example.imcommunity.service.ReplyService;
import com.example.imcommunity.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReplyServiceImpl implements ReplyService {
    private final CommentService commentService;
    private final UserService userService;
    private final ReplyRepository replyRepository;

    public ReplyServiceImpl(CommentService commentService, UserService userService, ReplyRepository replyRepository) {
        this.commentService = commentService;
        this.userService = userService;
        this.replyRepository = replyRepository;
    }

    @Override
    public Reply create(ReplyForm replyForm) {
        Reply newReply = new Reply();
        User user = userService.findUserById(replyForm.getUserId());
        Comment comment = commentService.findCommentById(replyForm.getCommentId());

        newReply.setContent(replyForm.getContent());
        newReply.setUser(user);
        newReply.setComment(comment);

        user.getReplies().add(newReply);
        comment.getReplies().add(newReply);

        newReply.setGmtCreated(new Date());
        newReply.setGmtModified(newReply.getGmtCreated());

        return replyRepository.save(newReply);
    }
}
