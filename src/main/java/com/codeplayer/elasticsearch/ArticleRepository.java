package com.codeplayer.elasticsearch;

import com.codeplayer.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository <Article,Long>{
}
