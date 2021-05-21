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

    PageDTO<ArticleDTO> profileArticlePageList(Long userId, Integer page, Integer size);

    ArticleDTO getById(Long id);

    void incView(Long id);

    List<ArticleDTO> selectRelated(ArticleDTO articleDTO);

    List<Article> searchFromEs(String content);

    long ArticleCount();

    Article findById(long parseLong);

    List<Article> findAllArticle();
}
