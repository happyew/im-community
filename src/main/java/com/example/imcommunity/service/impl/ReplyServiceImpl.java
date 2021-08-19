package com.example.imcommunity.service.impl;

import com.example.imcommunity.entity.Comment;
import com.example.imcommunity.entity.Reply;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.model.ReplyFrom;
import com.example.imcommunity.repository.ReplyRepository;
import com.example.imcommunity.service.CommentService;
import com.example.imcommunity.service.ReplyService;
import com.example.imcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public Reply create(ReplyFrom replyFrom) {
        Reply newReply = new Reply();
        User user = userService.findUserById(replyFrom.getUserId());
        Comment comment = commentService.findCommentById(replyFrom.getCommentId());

        newReply.setContent(replyFrom.getContent());
        newReply.setUser(user);
        newReply.setComment(comment);

        user.getReplies().add(newReply);
        comment.getReplies().add(newReply);

        newReply.setGmtCreated(new Date());
        newReply.setGmtModified(newReply.getGmtCreated());

        return replyRepository.save(newReply);
    }
}
