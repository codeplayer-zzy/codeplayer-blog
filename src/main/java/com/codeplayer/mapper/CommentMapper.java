package com.codeplayer.mapper;

import com.codeplayer.entity.Article;
import com.codeplayer.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @description: (comment)表数据库访问层
 * @author CodePlayer
 * @date 2021/4/6 20:26
 */
@Mapper
public interface CommentMapper {
    @Select("SELECT * FROM comment WHERE comment_id = #{parentId}")
    Comment findById(@Param(value = "parentId") Long parentId);

    @Insert("INSERT INTO comment (parent_id,content,type,comment_count,like_count,gmt_create,gmt_modified,commentator)" +
            "VALUES (#{parentId},#{content},#{type},#{commentCount},#{likeCount},#{gmtCreate},#{gmtModified},#{commentator})")
    void insert(Comment comment);

    @Update("UPDATE article SET comment_count = #{commentCount} WHERE article_id = #{articleId}")
    void incArticleCommentCount(Article article);

    @Update("UPDATE comment SET comment_count = #{commentCount} WHERE comment_id = #{commentId}")
    void incCommentCount(Comment commentCount);

    @Select("SELECT * FROM comment WHERE parent_id = #{parentId} AND type = #{type} ORDER BY gmt_create DESC")
    List<Comment> findByIdOrType(@Param(value = "parentId") Long id, @Param(value = "type") Integer type);


}
