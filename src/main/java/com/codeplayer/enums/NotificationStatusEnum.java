package com.codeplayer.enums;

public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1)
    ;

    private Integer Status;

    public Integer getStatus() {
        return Status;
    }

    NotificationStatusEnum(Integer status) {
        Status = status;
    }
}
