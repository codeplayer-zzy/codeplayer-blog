package com.codeplayer.service;

import com.codeplayer.elasticsearch.ArticleRepository;
import com.codeplayer.mapper.ArticleMapper;
import com.codeplayer.mapper.CommentMapper;
import com.codeplayer.mapper.NotificationMapper;
import com.codeplayer.mapper.UserMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import javax.management.Notification;

public class BaseService {
    @Resource
    protected UserMapper userMapper;

    @Resource
    protected ArticleMapper articleMapper;

    @Resource
    protected CommentMapper commentMapper;

    @Resource
    protected NotificationMapper notificationMapper;

    @Resource
    protected ArticleRepository articleRepository;

    @Autowired
    protected RestHighLevelClient client;
}
