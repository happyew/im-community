package com.example.imcommunity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Gitee用户数据传输对象
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiteeUserDTO {
    private String id;
    private String login;
    private String name;
    private String avatarUrl;
    private String htmlUrl;
}
