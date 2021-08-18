package com.example.imcommunity.model;

import lombok.Data;

@Data
public class CommentFrom {
    private String content;
    private Long userId;
    private Long questionId;
}
