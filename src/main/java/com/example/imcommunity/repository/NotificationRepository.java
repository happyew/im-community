package com.example.imcommunity.repository;

import com.example.imcommunity.entity.Notification;
import com.example.imcommunity.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findByUser(User user);

    Page<Notification> findByUser(User user, Pageable pageable);
}