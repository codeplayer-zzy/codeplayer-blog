package com.codeplayer.dto;

import lombok.Data;

@Data
public class ArticleQueryDTO {
    private Integer page;
    private Integer size;
    private String tag;
    private Integer offset;
}
