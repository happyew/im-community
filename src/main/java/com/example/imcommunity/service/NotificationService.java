package com.example.imcommunity.service;

import com.example.imcommunity.dto.NotificationPageDTO;
import com.example.imcommunity.entity.Comment;
import com.example.imcommunity.entity.Notification;
import com.example.imcommunity.entity.Reply;
import com.example.imcommunity.entity.User;

public interface NotificationService {
    void create(Comment comment, User notifier);

    void create(Reply reply, User notifier);

    NotificationPageDTO findNotificationPageDTOByUser(User user, Integer page, Integer size);

    Notification setRead(Long id);
}
