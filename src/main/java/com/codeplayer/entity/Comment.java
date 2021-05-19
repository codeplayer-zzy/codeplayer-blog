package com.codeplayer.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {
    private static final long serialVersionUID = 1208413785129978561L;
    @Id
    private Long commentId;
    private Long parentId;
    private String content;
    private Integer type;
    private Long likeCount;
    private Long commentCount;
    private Long commentator;
    private Date gmtCreate;
    private Date gmtModified;
}
