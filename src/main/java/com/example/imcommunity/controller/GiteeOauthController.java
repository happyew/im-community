package com.example.imcommunity.controller;

import com.example.imcommunity.dto.AccessTokenDTO;
import com.example.imcommunity.dto.GiteeTokenDTO;
import com.example.imcommunity.dto.GiteeUserDTO;
import com.example.imcommunity.dto.GithubUserDTO;
import com.example.imcommunity.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GiteeOauthController {
    @Autowired
    GiteeProvider giteeProvider;

    @GetMapping("/login")
    public String login(@RequestParam(name = "code") String code){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClientSecret("2d2dcf2d77ca099d0a579873d70adeebd9043e992094b614ea0f50375d64f520");
        accessTokenDTO.setClientId("eb00a0698b705d167196002dfe4392d69aad1b743525c72a6b617b5a8acece40");
        accessTokenDTO.setRedirectUri("http://localhost:8080/login");
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUserDTO user = giteeProvider.getUser(accessToken);
        System.out.println(user);
        return "redirect:/";
    }
}
