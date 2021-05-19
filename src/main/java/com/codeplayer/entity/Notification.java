package com.codeplayer.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Notification {
    private Long notificationId;
    private Long notifier;
    private String notifierName;
    private Long receiver;
    private Long outerId;
    private String outerTitle;
    private Integer type;
    private Integer status;
    private Date gmtCreate;
}
