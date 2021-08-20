package com.example.imcommunity.service;

import com.example.imcommunity.entity.Comment;
import com.example.imcommunity.model.CommentForm;

public interface CommentService {
    Comment create(CommentForm commentForm);

    Comment findCommentById(Long id);
}
