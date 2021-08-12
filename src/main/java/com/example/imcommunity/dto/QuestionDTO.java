package com.example.imcommunity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDTO {
    private String title;
    private String description;
    private String tag;
    private Date gmtCreate;
    private Date gmtModified;
    private Long viewCount;
    private Long commentCount;
    private Long likeCount;
    private String avatarUrl;
}
