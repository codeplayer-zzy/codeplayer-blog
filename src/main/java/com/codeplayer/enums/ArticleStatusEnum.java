package com.codeplayer.enums;

public enum ArticleStatusEnum {
    PUBLISHED(1),//已发布
    DRAFT(2),//草稿
    ;

    private Integer Status;
    public Integer getStatus() {
        return Status;
    }
    ArticleStatusEnum(Integer status) {
        Status = status;
    }
}
