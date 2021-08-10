package com.example.imcommunity.controller;

import com.example.imcommunity.dto.GithubTokenDTO;
import com.example.imcommunity.dto.GiteeUserDTO;
import com.example.imcommunity.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GiteeOauthController {
    @Autowired
    private GiteeProvider giteeProvider;
    @Value("${gitee.client.id}")
    private String clientId;
    @Value("${gitee.client.secret}")
    private String clientSecret;
    @Value("${gitee.redirect.uri}")
    private String redirectUri;

    @GetMapping("/login")
    public String login(@RequestParam(name = "code") String code, HttpServletRequest request) {
        GithubTokenDTO githubTokenDTO = new GithubTokenDTO();
        githubTokenDTO.setCode(code);
        githubTokenDTO.setClientId(clientId);
        githubTokenDTO.setClientSecret(clientSecret);
        githubTokenDTO.setRedirectUri(redirectUri);
        String accessToken = giteeProvider.getAccessToken(githubTokenDTO);
        GiteeUserDTO user = giteeProvider.getUser(accessToken);
        if (user != null) {
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/";
    }
}
