package com.example.imcommunity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Gitee用户认证信息传输对象
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiteeTokenDTO {
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String refreshToken;
    private String scope;
    private String createAt;
    private String code;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
