package com.example.imcommunity.controller;

import com.example.imcommunity.dto.GiteeTokenDTO;
import com.example.imcommunity.dto.GiteeUserDTO;
import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.provider.GiteeProvider;
import com.example.imcommunity.repository.GiteeUserRepository;
import com.example.imcommunity.service.GiteeUserService;
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
    private final GiteeProvider giteeProvider;
    private final GiteeUserService giteeUserService;
    @Value("${gitee.client.id}")
    private String clientId;
    @Value("${gitee.client.secret}")
    private String clientSecret;
    @Value("${gitee.redirect.uri}")
    private String redirectUri;

    public GiteeOauthController(GiteeProvider giteeProvider, GiteeUserService giteeUserService) {
        this.giteeProvider = giteeProvider;
        this.giteeUserService = giteeUserService;
    }

    /**
     * 登录
     *
     * @param code     从Gitee返回的码
     * @param response 响应
     * @return 视图
     */
    @GetMapping("/login")
    public String login(@RequestParam(name = "code") String code,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        GiteeTokenDTO giteeTokenDTO = new GiteeTokenDTO();
        giteeTokenDTO.setCode(code);
        giteeTokenDTO.setClientId(clientId);
        giteeTokenDTO.setClientSecret(clientSecret);
        giteeTokenDTO.setRedirectUri(redirectUri);
        String accessToken = giteeProvider.getAccessToken(giteeTokenDTO);
        GiteeUserDTO user = giteeProvider.getUser(accessToken);
        if (user != null) {
            GiteeUser giteeUserExist = giteeUserService.findByAccountId(user.getId());
            if (giteeUserExist != null) {
                request.getSession().setAttribute("user", giteeUserExist);
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
            GiteeUser giteeUserSaved = giteeUserService.save(newGiteeUser);
            if (giteeUserSaved.getAccountId() != null) {
                request.getSession().setAttribute("user", giteeUserSaved);
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
        request.getSession().removeAttribute("user");
        // 清除cookie里的token
        response.addCookie(new Cookie("token", null));
        return "redirect:/";
    }
}
