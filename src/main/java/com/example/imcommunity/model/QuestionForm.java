package com.example.imcommunity.model;

import lombok.Data;

@Data
public class QuestionForm {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long userId;
}
