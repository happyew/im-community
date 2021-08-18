package com.example.imcommunity.model;

import lombok.Data;

@Data
public class QuestionFrom {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long userId;
}
