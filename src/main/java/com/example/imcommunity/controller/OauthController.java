package com.example.imcommunity.controller;

import com.example.imcommunity.dto.AccessTokenDTO;
import com.example.imcommunity.dto.GithubUserDTO;
import com.example.imcommunity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClientId("6b9551d7a3bf814322ab");
        accessTokenDTO.setClientSecret("134f9ad51dadddb00ea426dab2884543d9f83760");
        accessTokenDTO.setRedirectUri("http://localhost:8080/success");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO user = githubProvider.getUser(accessToken);
        System.out.println(user);
        return "index";
    }
}
