package com.codeplayer.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentResultDTO implements Serializable {
    private static final long serialVersionUID = 5732373207306086121L;
    private Long parentId;
    private String content;
    private Integer type;
}
