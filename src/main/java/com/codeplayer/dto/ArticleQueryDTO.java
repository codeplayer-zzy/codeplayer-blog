package com.codeplayer.dto;

import lombok.Data;

@Data
public class ArticleQueryDTO {
    private Integer page;
    private Integer size;
    private String tag;
    private String sort;
    private Integer offset;
    private Long time;
    private Long userId;
    private Integer status;
}
