package com.example.imcommunity.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotificationPageDTO {
    private Integer previousNumber = 0;
    private Integer currentNumber = 0;
    private Integer nextNumber = 0;
    private Integer startNumber = 0;
    private Integer endNumber = 0;
    private List<NotificationDTO> notificationDTOS;
}
