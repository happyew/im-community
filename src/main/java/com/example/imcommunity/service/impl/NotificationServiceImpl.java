package com.example.imcommunity.service.impl;

import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import com.example.imcommunity.dto.NotificationDTO;
import com.example.imcommunity.dto.NotificationPageDTO;
import com.example.imcommunity.entity.*;
import com.example.imcommunity.enums.NotificationStatus;
import com.example.imcommunity.enums.NotificationType;
import com.example.imcommunity.exception.CustomErrorCode;
import com.example.imcommunity.exception.CustomException;
import com.example.imcommunity.repository.NotificationRepository;
import com.example.imcommunity.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void create(Comment comment, User notifier) {
        Question question = comment.getQuestion();
        Notification notification = new Notification();
        notification.setNotifierName(notifier.getUsername());
        notification.setStatus(NotificationStatus.NOT_READ);
        notification.setTitle(question.getTitle());
        notification.setGmtCreated(new Date());
        notification.setUser(question.getUser());
        notification.setType(NotificationType.QUESTION);
        notification.setUrl(StrUtil.format("/question/{}", question.getId()));
        notificationRepository.save(notification);
    }

    @Override
    public void create(Reply reply, User notifier) {
        Comment comment = reply.getComment();
        User user = comment.getUser();
        Notification notification = new Notification();
        notification.setNotifierName(notifier.getUsername());
        notification.setStatus(NotificationStatus.NOT_READ);
        notification.setTitle(comment.getQuestion().getTitle());
        notification.setGmtCreated(new Date());
        notification.setUser(user);
        notification.setType(NotificationType.COMMENT);
        notification.setUrl(StrUtil.format("/question/{}", comment.getQuestion().getId()));
        user.getNotifications().add(notification);
        notificationRepository.save(notification);
    }

    @Override
    public NotificationPageDTO findNotificationPageDTOByUser(User user, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "gmtCreated");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Notification> notifications = notificationRepository.findByUser(user, pageable);
        int totalPages = notifications.getTotalPages();
        ArrayList<NotificationDTO> notificationDTOS = new ArrayList<>();
        notifications.forEach(notification -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setType(notification.getType().getName());
            notificationDTO.setStatus(notification.getStatus().getCode());
            notificationDTOS.add(notificationDTO);
        });
        NotificationPageDTO notificationPageDTO = new NotificationPageDTO();
        notificationPageDTO.setNotificationDTOS(notificationDTOS);
        int[] rainbow = PageUtil.rainbow(page, totalPages, 10);
        int length = rainbow.length;
        if (length > 1) {
            notificationPageDTO.setCurrentNumber(page);
            notificationPageDTO.setStartNumber(rainbow[0]);
            notificationPageDTO.setEndNumber(rainbow[length - 1]);
            if (notifications.hasPrevious()) {
                notificationPageDTO.setPreviousNumber(page - 1);
            } else {
                notificationPageDTO.setPreviousNumber(-1);
            }
            if (notifications.hasNext()) {
                notificationPageDTO.setNextNumber(page + 1);
            } else {
                notificationPageDTO.setNextNumber(-1);
            }
        }
        return notificationPageDTO;
    }

    @Override
    public Notification setRead(Long id) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            if (notification.getStatus().equals(NotificationStatus.NOT_READ)) {
                notification.setStatus(NotificationStatus.READ);
                return notificationRepository.save(notification);
            }
        }
        throw new CustomException(CustomErrorCode.BAD_REQUEST);
    }
}
