package com.codeplayer.dto;

import com.codeplayer.entity.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
public class ArticleDTO implements Serializable{
    private static final long serialVersionUID = 1340313767738953037L;
    @Id
    private Long articleId;
    private String title;
    private Object content;
    private String tag;
    private Integer status;
    private Long creator;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;
    private Date gmtCreate;
    private Date gmtModified;
    private User user;
}
