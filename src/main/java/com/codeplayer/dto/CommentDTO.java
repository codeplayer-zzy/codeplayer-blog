package com.codeplayer.dto;

import com.codeplayer.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentDTO implements Serializable {
    private static final long serialVersionUID = -6828992474180461124L;
    private Long commentId;
    private Long parentId;
    private String content;
    private Integer type;
    private Long likeCount;
    private Long commentCount;
    private Long commentator;
    private Date gmtCreate;
    private Date gmtModified;
    private User user;
}
