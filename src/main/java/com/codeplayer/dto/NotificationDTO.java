package com.codeplayer.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NotificationDTO implements Serializable {
    private static final long serialVersionUID = 8629741351306338367L;
    private Long notificationId;
    private Long notifier;
    private String notifierName;
    private Long outerId;
    private String outerTitle;
    private Integer type;
    private Integer status;
    private String typeName;
    private Date gmtCreate;
}
