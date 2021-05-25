package com.codeplayer.mapper;

import com.codeplayer.dto.ArticleQueryDTO;
import com.codeplayer.entity.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Date;
import java.util.List;
/**
 * @description: (article)表数据库访问层
 * @author CodePlayer
 * @date 2021/4/6 20:26
 */
@Mapper
public interface ArticleMapper {
    @Select({"<script> " +
            "SELECT count(1) FROM article " +
            "   <where> " +
            "       <if test=\"status != null and status != ''\">status = #{status}</if> " +
            "       <if test=\"tag != null and tag != ''\"> and tag = #{tag}</if> " +
            "       <if test=\"userId != null and userId != ''\"> and creator = #{userId}</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'no'\"> and comment_count = 0</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'good'\"> and comment_count = 5</if> " +
            "       <if test=\"time != null and time != ''\"> and gmt_create > #{time}</if> " +
            "   </where> " +
            "</script>"})
    Integer countByArticleQueryDTO(ArticleQueryDTO articleQueryDTO);

    @Insert("INSERT INTO article (title,content,creator,comment_count,view_count,like_count,status,tag,gmt_create,gmt_modified)" +
            "VALUES (#{title},#{content},#{creator},#{commentCount},#{viewCount},#{likeCount},#{status},#{tag},#{gmtCreate},#{gmtModified})")
    void create(Article article);

    @Select({"<script> " +
            "SELECT * FROM article " +
            "   <where> " +
            "       <if test=\"status != null and status != ''\">status = #{status}</if> " +
            "       <if test=\"tag != null and tag != ''\"> and tag = #{tag}</if> " +
            "       <if test=\"userId != null and userId != ''\"> and creator = #{userId}</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'no'\"> and comment_count = 0</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'good'\"> and comment_count = 5</if> " +
            "       <if test=\"time != null and time != ''\"> and gmt_create > #{time}</if> " +
            "   </where> " +
            "       <if test=\"sort == null and sort != ''\">ORDER BY gmt_create DESC</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'new'\">ORDER BY gmt_create DESC</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'no'\">ORDER BY gmt_create DESC</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'good'\">ORDER BY gmt_create DESC</if> " +
            "       <if test=\"sort != null and sort != '' and (sort == 'hot7' || sort == 'hot30' || sort == 'hot')\">ORDER BY comment_count DESC</if> " +
            "   LIMIT #{offset},#{size} " +
            "</script>"})
    List<Article> articlePageList(ArticleQueryDTO articleQueryDTO);


    @Update("UPDATE article SET title = #{title}, content = #{content}, tag = #{tag}, status = #{status}, gmt_modified = #{gmtModified} WHERE article_id = #{articleId}")
    void update(Article article);

    @Update("UPDATE article SET view_count = #{viewCount} WHERE article_id = #{articleId}")
    void incView(Article article);

    @Select("SELECT * FROM article WHERE article_id = #{articleId}")
    Article findById(@Param(value = "articleId") Long articleId);

    @Select("SELECT * FROM article WHERE status = #{status}")
    List<Article> findByPublishStatus(@Param(value = "status") Integer status);

    @Select("SELECT * FROM article WHERE article_id != #{articleId} AND tag regexp #{tag}")
    List<Article> selectRelated(Article article);

    @Select("SELECT * FROM article ORDER BY gmt_create DESC")
    List<Article> findAll();

    @Update("UPDATE article SET status = #{status} WHERE article_id = #{articleId}")
    Integer updateById(@Param(value = "articleId") Long id, @Param(value = "status") Integer status);

    @Delete("DELETE FROM article WHERE article_id = #{articleId} AND status = #{status}")
    Integer deleteArticleByArticleId(@Param(value = "articleId") Long id, @Param(value = "status") Integer status);

    @Insert("INSERT INTO article (title,content,creator,comment_count,view_count,like_count,status,tag,gmt_create,gmt_modified)" +
            "VALUES (#{title},#{content},#{creator},#{commentCount},#{viewCount},#{likeCount},#{status},#{tag},#{gmtCreate},#{gmtModified})")
    Integer saveArticle(Article article);

}
