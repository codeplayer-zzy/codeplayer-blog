package com.codeplayer.service.impl;

import com.codeplayer.dto.ArticleDTO;
import com.codeplayer.dto.ArticleQueryDTO;
import com.codeplayer.dto.PageDTO;
import com.codeplayer.entity.Article;
import com.codeplayer.entity.User;
import com.codeplayer.enums.SortEnum;
import com.codeplayer.service.ArticleService;
import com.codeplayer.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service("articleService")
public class ArticleServiceImpl extends BaseService implements ArticleService {
    /**
     * @param article
     * 创建文章
     */
    @Override
    public void createOrUpdate(Article article) {
        if (article.getArticleId() == null){
            Article article1 = new Article();
            BeanUtils.copyProperties(article,article1);
            //创建
            article1.setGmtCreate(new Date());
            article1.setGmtModified(article.getGmtCreate());
            article1.setStatus(1);
            article1.setLikeCount(0L);
            article1.setViewCount(0L);
            article1.setCommentCount(0L);
            //创建文章
            articleMapper.create(article1);
        }else {
            article.setGmtModified(new Date());
            //更新文章
            articleMapper.update(article);
        }

    }
    /**
     * @param page
     * @param size
     * @param tag
     * @param sort
     * @return 文章分页查找
     */
    @Override
    public PageDTO<ArticleDTO> articlePageList(Integer page, Integer size, String tag, String sort) {
        ArticleQueryDTO articleQueryDTO = new ArticleQueryDTO();
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "").replace("*", "").replace("?", "");
            articleQueryDTO.setTag(tag);
        }
        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.name().toLowerCase(Locale.ENGLISH).equals(sort)){
                articleQueryDTO.setSort(sort);
                if (sortEnum == SortEnum.HOT7) {
                    articleQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
                }else if (sortEnum == SortEnum.HOT30) {
                    articleQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }

        Integer totalPage;
        Integer totalCount = articleMapper.countByArticleQueryDTO(articleQueryDTO);
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = page < 1 ? 0 : size * (page - 1);
        articleQueryDTO.setOffset(offset);
        articleQueryDTO.setSize(size);
        List<Article> articleList = articleMapper.articlePageList(articleQueryDTO);
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        for (Article article : articleList) {
            User user = userMapper.findByCreatorId(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article,articleDTO);
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }
        pageDTO.setData(articleDTOList);
        pageDTO.setPagination(totalPage, page);
        return pageDTO;
    }
    /**
     * @param userId
     * @param page
     * @param size
     * @return profileArticle分页查找
     */
    @Override
    public PageDTO<ArticleDTO> profileArticlePageList(Long userId, Integer page, Integer size) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        Integer totalPage = null;
        Integer totalCount = articleMapper.countByColumn(userId);
        if (totalCount % size == 0){
            totalPage = totalCount / size;
            if (totalPage == 0){
                totalPage = 1;
            }
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        pageDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);
        List<Article> articleList = articleMapper.profileArticlePageList(userId,offset, size);
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article article : articleList) {
            User user = userMapper.findByCreatorId(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article,articleDTO);
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }
        pageDTO.setData(articleDTOList);
        return pageDTO;
    }

    /**
     * @param id
     * @return 查找文章内容 返回articleDTO
     */
    @Override
    public ArticleDTO getById(Long id) {
        Article article = articleMapper.findById(id);
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article,articleDTO);
        User user = userMapper.findByCreatorId(article.getCreator());
        articleDTO.setUser(user);
        return articleDTO;
    }

    /**
     *
     * @param parseLong
     * @return 查找文章内容 返回article
     */
    @Override
    public Article findById(long parseLong) {
        Article article = articleMapper.findById(parseLong);
        return article;
    }

    /**
     *
     * @return 返回所有文章list
     */
    @Override
    public List<Article> findAllArticle() {
        List<Article> articleList = articleMapper.findAll();
        return articleList;
    }

    /**
     * @param id
     * 自增阅读数
     */
    @Override
    public void incView(Long id) {
        Article article = articleMapper.findById(id);
        Article updateArticle = new Article();
        updateArticle.setArticleId(id);
        updateArticle.setViewCount(article.getViewCount() + 1L);
        articleMapper.incView(updateArticle);
    }

    /**
     * @param articleDTO
     * @return 查找相关文章
     */
    @Override
    public List<ArticleDTO> selectRelated(ArticleDTO articleDTO) {
        if (StringUtils.isBlank(articleDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(articleDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Article article = new Article();
        article.setArticleId(articleDTO.getArticleId());
        article.setTag(regexpTag);

        List<Article> articleList = articleMapper.selectRelated(article);
        if (articleList.size() == 0){
            return new ArrayList<>();
        }
        List<ArticleDTO> articleDTOS = articleList.stream().map(q -> {
            ArticleDTO articleDTO1 = new ArticleDTO();
            BeanUtils.copyProperties(q, articleDTO1);
            return articleDTO1;
        }).collect(Collectors.toList());
        return articleDTOS;
    }
    /**
     *
     * @param content
     * @return 从ES中搜索结果并高亮显示
     */
    @Override
    public List<Article> searchFromEs(String content) {
        List<Article> articleList = new ArrayList<>();

        //判断content是否为空
        if (StringUtils.isBlank(content)){
            return new ArrayList<>();
        }

        // 构建查询条件
        SearchRequest searchRequest = new SearchRequest("codeplayer");
        //创建搜索对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置查询条件
        content = QueryParser.escape(content).trim().replace(" ","");// 主要就是这一句把特殊字符都转义,那么lucene就可以识别,保证程序不报错
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(content, "title", "content", "tag"))
                .from(0)
                .size((int) ArticleCount())
                .sort("gmtCreate", SortOrder.valueOf("DESC"))
                .highlighter(new HighlightBuilder().field("*").requireFieldMatch(false).preTags("<span style='color:red;font-weight:500'>").postTags("</span>"));

        searchRequest.source(searchSourceBuilder);
        //执行搜索
        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Article article = new Article();
            article.setArticleId(Long.valueOf((Integer) sourceAsMap.get("articleId")));
            //设置title高亮
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("title")){
                article.setTitle(highlightFields.get("title").fragments()[0].toString());
            }
            //设置tag高亮
            if (highlightFields.containsKey("tag")){
                article.setTitle(highlightFields.get("tag").fragments()[0].toString());
            }
            Article article1 = articleMapper.findById(article.getArticleId());
            articleList.add(article1);
        }
        return articleList;
    }
    /**
     *
     * @return 获取ES中文档的数量
     */
    @Override
    public long ArticleCount() {
        // 创建搜索请求
        SearchRequest searchRequest = new SearchRequest("codeplayer");
        // 创建搜索对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置查询条件
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.getHits().getTotalHits().value;
    }

}
