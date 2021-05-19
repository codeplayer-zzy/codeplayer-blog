package com.codeplayer;

import com.codeplayer.elasticsearch.ArticleRepository;
import com.codeplayer.entity.Article;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.io.IOException;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void es() {
        articleRepository.deleteAll();
        System.out.println("gg");
    }

}
