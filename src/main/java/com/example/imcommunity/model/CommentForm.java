package com.example.imcommunity.model;

import lombok.Data;

@Data
public class CommentForm {
    private String content;
    private Long userId;
    private Long questionId;
}
