package com.example.imcommunity.interceptor;

import com.example.imcommunity.entity.User;
import com.example.imcommunity.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 配置拦截器
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {
    private final UserService userService;

    public SessionInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        /*Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    GiteeUser giteeUser = giteeUserService.findByToken(cookie.getValue());
                    if (giteeUser != null) {
                        request.getSession().setAttribute("user", giteeUser);
                        break;
                    }
                }
            }
        }
        return true;*/
        Subject subject = SecurityUtils.getSubject();
        HttpSession session = request.getSession();
        if (subject.isRemembered() || subject.isAuthenticated()) {
            String principal = (String) subject.getPrincipal();
            User user = userService.findByUsername(principal);
//            System.out.println(user);
            if (user != null) {
                session.setAttribute("user", user);
                return true;
            }
        }
        session.setAttribute("user", null);
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//        User user = (User) request.getSession().getAttribute("user");
//        if (user != null) {
//            modelAndView.addObject("user", user);
//        }
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
