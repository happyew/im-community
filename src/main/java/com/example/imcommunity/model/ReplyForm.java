package com.example.imcommunity.model;

import lombok.Data;

@Data
public class ReplyForm {
    private Long questionId;
    private Long userId;
    private Long commentId;
    private String content;
}
