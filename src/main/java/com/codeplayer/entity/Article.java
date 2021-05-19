package com.codeplayer.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "codeplayer")
public class Article implements Serializable {
    private static final long serialVersionUID = -4464567572629455945L;
    /**
     * 主键id
     */
    @Id
    private Long articleId;
    /**
     * 文章标题
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")//ik_max_word 或 ik_smart
    private String title;
    /**
     * 文章内容
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")//ik_max_word 或 ik_smart
    private Object content;
    /**
     * 文章标签
     */
    @Field(type = FieldType.Text)
    private String tag;
    /**
     * 文章状态 0草稿 1发布 2回收站
     */
    private int status;
    /**
     * 文章创建者
     */
    private Long creator;
    /**
     * 文章评论数
     */
    private Long commentCount;
    /**
     * 文章阅读数
     */
    private Long viewCount;
    /**
     * 文章喜欢数
     */
    private Long likeCount;
    /**
     * 文章创建时间
     */
    private Date gmtCreate;
    /**
     * 文章修改时间
     */
    private Date gmtModified;
}
