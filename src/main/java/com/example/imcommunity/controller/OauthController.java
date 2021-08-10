package com.example.imcommunity.controller;

import com.example.imcommunity.dto.GithubTokenDTO;
import com.example.imcommunity.dto.GithubUserDTO;
import com.example.imcommunity.provider.GithubProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OauthController {
    final GithubProvider githubProvider;

    public OauthController(GithubProvider githubProvider) {
        this.githubProvider = githubProvider;
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        GithubTokenDTO githubTokenDTO = new GithubTokenDTO();
        githubTokenDTO.setCode(code);
        githubTokenDTO.setState(state);
        githubTokenDTO.setClientId("6b9551d7a3bf814322ab");
        githubTokenDTO.setClientSecret("134f9ad51dadddb00ea426dab2884543d9f83760");
        githubTokenDTO.setRedirectUri("http://localhost:8080/success");
        String accessToken = githubProvider.getAccessToken(githubTokenDTO);
        GithubUserDTO user = githubProvider.getUser(accessToken);
        System.out.println(user);
        return "index";
    }
}
