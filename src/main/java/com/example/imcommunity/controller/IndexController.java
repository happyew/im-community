package com.example.imcommunity.controller;

import com.example.imcommunity.dto.QuestionPageDTO;
import com.example.imcommunity.service.QuestionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 首页控制器
 */
@Controller
public class IndexController {
    private static final String PAGE = "1";
    private static final String SIZE = "10";
    private final QuestionService questionService;

    public IndexController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * 首页
     *
     * @param model 模型
     * @return 视图
     */
    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = PAGE) Integer page,
                        @RequestParam(name = "size", defaultValue = SIZE) Integer size,
                        Model model) {
        if (page < 1) {
            page = Integer.getInteger(PAGE);
        }
        if (size < 1) {
            size = Integer.getInteger(SIZE);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "gmtModified");
        QuestionPageDTO questionPageDTO = questionService.findAllPage(page, size, sort);
        model.addAttribute("questionPageDTO", questionPageDTO);
        return "index";
    }
}
