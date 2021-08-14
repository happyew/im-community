package com.example.imcommunity.controller;

import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.entity.GiteeUser;
import com.example.imcommunity.service.QuestionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户个人资料控制器
 */
@Controller
public class UserProfileController {
    private final QuestionService questionService;

    public UserProfileController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * 显示用户本人的所有提问
     *
     * @param page    页数
     * @param size    单页元素长度
     * @param request 请求
     * @param model   模型
     * @return 视图
     */
    @GetMapping("/profile/question")
    public String profile(@RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          HttpServletRequest request, Model model) {
        GiteeUser giteeUser = (GiteeUser) request.getSession().getAttribute("user");
        if (giteeUser != null) {
            Sort sort = Sort.by(Sort.Direction.DESC, "gmtModified");
            QuestionPageDTO questionPageDTO = questionService.findPageByGiteeUser(page, size, sort, giteeUser);
            model.addAttribute("questionPageDTO", questionPageDTO);
            request.getSession().setAttribute("user", giteeUser);
            return "profile";
        }
        return "redirect:/";
    }
}
