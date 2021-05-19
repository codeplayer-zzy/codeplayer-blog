package com.codeplayer.enums;

public enum NotificationTypeEnum {
    REPLY_ARTICLE(1,"回复了文章"),
    REPLY_COMMENT(2,"回复了评论")
    ;

    private Integer type;
    private String name;

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    NotificationTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()){
            if (notificationTypeEnum.getType() == type){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
