package com.example.imcommunity.service;

import com.example.imcommunity.entity.Comment;
import com.example.imcommunity.model.CommentFrom;

public interface CommentService {
    Comment create(CommentFrom commentFrom);
}
