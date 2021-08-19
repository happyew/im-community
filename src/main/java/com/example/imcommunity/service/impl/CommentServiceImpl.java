package com.example.imcommunity.service.impl;

import com.example.imcommunity.entity.Comment;
import com.example.imcommunity.entity.Question;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.model.CommentFrom;
import com.example.imcommunity.repository.CommentRepository;
import com.example.imcommunity.service.CommentService;
import com.example.imcommunity.service.QuestionService;
import com.example.imcommunity.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final QuestionService questionService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              UserService userService,
                              QuestionService questionService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.questionService = questionService;
    }

    @Override
    public Comment create(CommentFrom commentFrom) {
        User user = userService.findUserById(commentFrom.getUserId());
        Question question = questionService.findQuestionById(commentFrom.getQuestionId());
        Comment newComment = new Comment();
        BeanUtils.copyProperties(commentFrom, newComment);
        newComment.setGmtCreated(new Date());
        newComment.setGmtModified(newComment.getGmtCreated());
        newComment.setUser(user);
        newComment.setQuestion(question);
        user.getComments().add(newComment);
        question.getComments().add(newComment);
        return commentRepository.save(newComment);
    }

    @Override
    public Comment findCommentById(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            return commentOptional.get();
        }
        return null;
    }
}
