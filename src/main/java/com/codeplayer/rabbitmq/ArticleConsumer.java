package com.codeplayer.rabbitmq;

import com.codeplayer.controller.PublishArticleController;
import com.codeplayer.dto.ArticleDTO;
import com.codeplayer.elasticsearch.ArticleRepository;
import com.codeplayer.entity.Article;
import com.codeplayer.mapper.ArticleMapper;
import com.codeplayer.service.ArticleService;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleConsumer {

    private static final Logger log = LoggerFactory.getLogger(ArticleConsumer.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;


    /**
     * 消费rabbitmq
     * @param message
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "es", type = "topic"),
                    key = {"article.save"}
            )
    })
    public void saveArticle(String message){
        log.warn("【RabbitMq】保存文章的消息，ID：" + message);
        Article article = articleService.findById(Long.parseLong(message));
        //创建ES索引数据
        articleRepository.save(article);
        log.warn("【RabbitMq】消费成功,创建ES索引数据");
    }

    /**
     * 消费rabbitmq
     * @param message
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "es", type = "topic"),
                    key = {"article.delete"}
            )
    })
    public void deleteArticle(String message){
        log.warn("【Rabbitmq】删除文章的消息，ID：" + message);
        //删除ES索引数据
        articleRepository.deleteById((int) Long.parseLong(message));
        log.warn("【RabbitMq】消费成功,删除ES索引数据");
    }
}
