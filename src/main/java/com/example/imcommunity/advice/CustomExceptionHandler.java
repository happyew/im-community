package com.example.imcommunity.advice;

import com.example.imcommunity.exception.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model) {
        if (e instanceof CustomException) {
            model.addAttribute("message", e.getMessage());
        } else {
            model.addAttribute("message", "系统异常...");
        }
        return new ModelAndView("error");
    }
}
