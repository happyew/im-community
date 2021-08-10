package com.example.imcommunity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiteeUserDTO {
    private String id;
    private String login;
    private String name;
    private String avatarUrl;
    private String htmlUrl;
}
