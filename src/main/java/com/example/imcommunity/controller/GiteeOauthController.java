package com.example.imcommunity.controller;

import com.example.imcommunity.dto.AccessTokenDTO;
import com.example.imcommunity.dto.GiteeTokenDTO;
import com.example.imcommunity.dto.GiteeUserDTO;
import com.example.imcommunity.dto.GithubUserDTO;
import com.example.imcommunity.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GiteeOauthController {
    @Autowired
    GiteeProvider giteeProvider;
    @Value("${gitee.client.id}")
    private String clientId;
    @Value("${gitee.client.secret}")
    private String clientSecret;
    @Value("${gitee.redirect.uri}")
    private String redirectUri;

    @GetMapping("/login")
    public String login(@RequestParam(name = "code") String code) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClientId(clientId);
        accessTokenDTO.setClientSecret(clientSecret);
        accessTokenDTO.setRedirectUri(redirectUri);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUserDTO user = giteeProvider.getUser(accessToken);
        System.out.println(user);
        return "redirect:/";
    }
}
