package com.example.imcommunity.controller;

import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.dto.NotificationPageDTO;
import com.example.imcommunity.entity.Notification;
import com.example.imcommunity.entity.User;
import com.example.imcommunity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/notification")
    public String show(HttpSession session, Model model,
                       @RequestParam(name = "page", defaultValue = "1") Integer page,
                       @RequestParam(name = "size", defaultValue = "10") Integer size) {
        User user = (User) session.getAttribute("user");
        NotificationPageDTO notificationPageDTO = notificationService.findNotificationPageDTOByUser(user, page, size);
        model.addAttribute("notificationPageDTO", notificationPageDTO);
        return "profileNotification";
    }

    @GetMapping("/profile/notification/{id}")
    public String show(@PathVariable Long id) {
        Notification notification = notificationService.setRead(id);
        return StrUtil.format("redirect:{}", notification.getUrl());
    }
}
