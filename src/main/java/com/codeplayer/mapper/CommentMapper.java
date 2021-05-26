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
    Comment findByCommentId(@Param(value = "parentId") Long parentId);

    @Select("SELECT * FROM comment WHERE parent_id = #{articleId}")
    List<Comment> findByParentId(@Param(value = "articleId") Long articleId);

    @Insert("INSERT INTO comment (parent_id,content,type,comment_count,like_count,gmt_create,gmt_modified,commentator)" +
            "VALUES (#{parentId},#{content},#{type},#{commentCount},#{likeCount},#{gmtCreate},#{gmtModified},#{commentator})")
    void insert(Comment comment);

    @Update("UPDATE article SET comment_count = #{commentCount} WHERE article_id = #{articleId}")
    void incArticleCommentCount(Article article);

    @Update("UPDATE comment SET comment_count = #{commentCount} WHERE comment_id = #{commentId}")
    void incCommentCommentCount(Comment comment);

    @Select("SELECT * FROM comment WHERE parent_id = #{parentId} AND type = #{type} ORDER BY gmt_create DESC")
    List<Comment> findByIdOrType(@Param(value = "parentId") Long id, @Param(value = "type") Integer type);

    @Delete("DELETE FROM comment WHERE parent_id = #{commentId}")
    void deleteByParentId(@Param(value = "commentId") Long commentId);

    @Delete("DELETE FROM comment WHERE comment_id = #{commentId}")
    Integer deleteByCommentId(@Param(value = "commentId") Long id);

    @Update("UPDATE comment SET like_count = #{likeCount} WHERE comment_id = #{commentId}")
    Integer CommentLikeCount(Comment comment);

}
