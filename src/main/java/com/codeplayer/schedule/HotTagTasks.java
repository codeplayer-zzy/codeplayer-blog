package com.codeplayer.schedule;

import com.codeplayer.cache.HotTagCache;
import com.codeplayer.entity.Article;
import com.codeplayer.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HotTagTasks {

    @Autowired
    private ArticleService  articleService;

    @Autowired
    private HotTagCache hotTagCache;

    private static final Logger log = LoggerFactory.getLogger(HotTagTasks.class);

    //权重计算priority=questionCount*5+commentCount*1+likeCount*10+viewCount/10
    @Scheduled(cron = "0 0 6 * * ?")//每天早上六点触发一次0 0 6 * * ? ，五秒钟执行一次*/5 * * * * ?
    public void hotTagSchedule() {
        log.info("hotTagSchedule start {}", new Date());

        Map<String, Long> priorities = new HashMap<>();//map是无序的
        List<Article> allArticle = articleService.findAllArticle();
        allArticle.forEach(article -> {
            String[] tags = StringUtils.split(article.getTag(), ",");
            for (String tag : tags) {
                Long priority = priorities.get(tag);
                if (priority != null) {
                    priorities.put(tag, priority + 5 + article.getCommentCount()+(article.getLikeCount()*10)+(article.getViewCount()/10));
                } else {
                    priorities.put(tag, 5 + article.getCommentCount()+(article.getLikeCount()*10)+(article.getViewCount()/10));
                }
            }
        });
        hotTagCache.updateTags(priorities);
        log.info("hotTagSchedule end {}", new Date());
    }
}
