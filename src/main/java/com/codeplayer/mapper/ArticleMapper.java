package com.codeplayer.mapper;

import com.codeplayer.dto.ArticleQueryDTO;
import com.codeplayer.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
/**
 * @description: (article)表数据库访问层
 * @author CodePlayer
 * @date 2021/4/6 20:26
 */
@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article (title,content,creator,comment_count,view_count,like_count,status,tag,gmt_create,gmt_modified)" +
            "VALUES (#{title},#{content},#{creator},#{commentCount},#{viewCount},#{likeCount},#{status},#{tag},#{gmtCreate},#{gmtModified})")
    void create(Article article);

    @Select({"<script> " +
            "SELECT * FROM article " +
            "   <where> " +
            "       <if test=\"tag != null and tag != ''\">AND tag = #{tag}</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'no'\">AND comment_count = 0</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'good'\">AND comment_count = 5</if> " +
            "       <if test=\"time != null and time != ''\">AND gmt_create > #{time}</if> " +
            "   </where> " +
            "       <if test=\"sort == null and sort != ''\">ORDER BY gmt_create DESC</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'new'\">ORDER BY gmt_create DESC</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'no'\">ORDER BY gmt_create DESC</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'good'\">ORDER BY gmt_create DESC</if> " +
            "       <if test=\"sort != null and sort != '' and (sort == 'hot7' || sort == 'hot30' || sort == 'hot')\">ORDER BY comment_count DESC</if> " +
            "   LIMIT #{offset},#{size} " +
            "</script>"})
    List<Article> articlePageList(ArticleQueryDTO articleQueryDTO);


    @Select({"<script> " +
            "SELECT count(1) FROM article " +
            "   <where> " +
            "       <if test=\"tag != null and tag != ''\">AND tag = #{tag}</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'no'\">AND comment_count = 0</if> " +
            "       <if test=\"sort != null and sort != '' and sort == 'good'\">AND comment_count = 5</if> " +
            "       <if test=\"time != null and time != ''\">AND gmt_create > #{time}</if> " +
            "   </where> " +
            "</script>"})
    Integer countByArticleQueryDTO(ArticleQueryDTO articleQueryDTO);

    @Select("SELECT * FROM article WHERE creator = #{userId} ORDER BY gmt_create DESC LIMIT #{offset},#{size}")
    List<Article> profileArticlePageList(@Param(value = "userId") Long userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("SELECT count(1) FROM article WHERE creator = #{userId}")
    Integer countByColumn(@Param(value = "userId") Long userId);

    @Update("UPDATE article SET title = #{title}, content = #{content}, tag = #{tag},gmt_modified = #{gmtModified}")
    void update(Article article);

    @Update("UPDATE article SET view_count = #{viewCount} WHERE article_id = #{articleId}")
    void incView(Article article);

    @Select("SELECT * FROM article WHERE article_id = #{articleId}")
    Article findById(@Param(value = "articleId") Long articleId);

    @Select("SELECT * FROM article WHERE article_id != #{articleId} AND tag regexp #{tag}")
    List<Article> selectRelated(Article article);

    @Select("SELECT * FROM article ORDER BY gmt_create DESC")
    List<Article> findAll();
}
