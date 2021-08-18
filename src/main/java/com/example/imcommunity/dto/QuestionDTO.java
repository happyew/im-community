package com.example.imcommunity.dto;

import com.example.imcommunity.entity.Comment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Date gmtCreated;
    private Date gmtModified;
    private Long viewCount;
    private Long likeCount;
    private String avatarUrl;
    private String username;
    private Long userid;
    private List<Comment> comments = new ArrayList<>();
}
