package com.example.imcommunity.controller;

import com.example.imcommunity.dto.GiteeUserDTO;
import com.example.imcommunity.dto.GithubTokenDTO;
import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.provider.GiteeProvider;
import com.example.imcommunity.repository.GiteeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Gitee登录控制器
 */
@Controller
public class GiteeOauthController {
    @Autowired
    private GiteeProvider giteeProvider;
    @Autowired
    private GiteeUserRepository giteeUserRepository;
    @Value("${gitee.client.id}")
    private String clientId;
    @Value("${gitee.client.secret}")
    private String clientSecret;
    @Value("${gitee.redirect.uri}")
    private String redirectUri;

    /**
     * 登录
     *
     * @param code     从Gitee返回的码
     * @param response 响应
     * @param model    模型
     * @return 视图
     */
    @GetMapping("/login")
    public String login(@RequestParam(name = "code") String code, HttpServletResponse response) {
        GithubTokenDTO githubTokenDTO = new GithubTokenDTO();
        githubTokenDTO.setCode(code);
        githubTokenDTO.setClientId(clientId);
        githubTokenDTO.setClientSecret(clientSecret);
        githubTokenDTO.setRedirectUri(redirectUri);
        String accessToken = giteeProvider.getAccessToken(githubTokenDTO);
        GiteeUserDTO user = giteeProvider.getUser(accessToken);
        if (user != null) {
            GiteeUser giteeUserExist = giteeUserRepository.findByAccountId(user.getId());
            if (giteeUserExist != null) {
                response.addCookie(new Cookie("token", giteeUserExist.getToken()));
                return "redirect:/";
            }
            GiteeUser newGiteeUser = new GiteeUser();
            Date dateTime = new Date();
            newGiteeUser.setAccountId(user.getId());
            newGiteeUser.setName(user.getName());
            newGiteeUser.setAvatarUrl(user.getAvatarUrl());
            newGiteeUser.setGmt_create(dateTime);
            newGiteeUser.setGmt_modified(dateTime);
            GiteeUser giteeUserSaved = giteeUserRepository.save(newGiteeUser);
            if (giteeUserSaved.getAccountId() != null) {
                response.addCookie(new Cookie("token", giteeUserSaved.getToken()));
                return "redirect:/";
            }
        }
        return "loginFailed";
    }

    /**
     * 登出
     *
     * @param request  请求
     * @param response 响应
     * @return 视图
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    // 清除cookie里的token
                    response.addCookie(new Cookie("token", null));
                }
            }
        }
        return "redirect:/";
    }
}
