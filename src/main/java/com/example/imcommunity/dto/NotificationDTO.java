package com.example.imcommunity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDTO {
    private Long id;
    private String notifierName;
    private String title;
    private String url;
    private String type;
    private Date gmtCreated;
    private int status;
}
