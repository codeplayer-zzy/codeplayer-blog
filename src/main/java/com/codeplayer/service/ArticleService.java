package com.codeplayer.service;

import com.codeplayer.dto.ArticleDTO;
import com.codeplayer.dto.PageDTO;
import com.codeplayer.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    void createOrUpdate(Article article);

    PageDTO<ArticleDTO> articlePageList(Integer page, Integer size, String tag, String sort);

    ArticleDTO getById(Long id);

    void incView(Long id);

    List<ArticleDTO> selectRelated(ArticleDTO articleDTO);

    List<Article> searchFromEs(String content);

    long ArticleCount();

    Article findById(Long parseLong);

    List<Article> findByPublishStatus(Integer status);

    Integer delArticleByArticleId(Long id);

    PageDTO<ArticleDTO> profileMultiPageList(Long userId, Integer page, Integer size, Integer status);

    Integer deleteArticleByArticleId(Long id);

    Integer saveArticle(Article article);

    Integer articleAllPublish(Long userId, Integer status);

    List<Article> findByUserIdAndPublishStatus(Long userId, Integer status);
}
