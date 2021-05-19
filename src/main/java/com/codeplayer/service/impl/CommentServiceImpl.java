package com.codeplayer.service.impl;

import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleReturningClause;
import com.codeplayer.dto.CommentDTO;
import com.codeplayer.entity.Article;
import com.codeplayer.entity.Comment;
import com.codeplayer.entity.Notification;
import com.codeplayer.entity.User;
import com.codeplayer.enums.CommentTypeEnum;
import com.codeplayer.enums.NotificationStatusEnum;
import com.codeplayer.enums.NotificationTypeEnum;
import com.codeplayer.exception.CustomizeErrorCode;
import com.codeplayer.exception.CustomizeException;
import com.codeplayer.service.BaseService;
import com.codeplayer.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("commentService")
public class CommentServiceImpl extends BaseService implements CommentService {
    @Override
    @Transactional
    public void insert(Comment comment, User user) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbcomment = commentMapper.findById(comment.getParentId());
            if (dbcomment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //当前评论的文章
            Article article = articleMapper.findById(dbcomment.getParentId());
            if (article == null){
                throw new CustomizeException(
                        CustomizeErrorCode.ARTICLE_NOT_FOUND);
            }
            commentMapper.insert(comment);
            Comment comment1 = new Comment();
            comment1.setCommentId(comment.getParentId());
            comment1.setCommentCount(dbcomment.getCommentCount() + 1L);
            commentMapper.incCommentCount(comment1);
            //创建通知
            createNotify(comment, dbcomment.getCommentator(), user.getName(), article.getTitle(), NotificationTypeEnum.REPLY_COMMENT,article.getArticleId());
        }else {
            //回复问题
            Article article = articleMapper.findById(comment.getParentId());
            if (article == null){
                throw new CustomizeException(
                        CustomizeErrorCode.ARTICLE_NOT_FOUND);
            }
            commentMapper.insert(comment);
            article.setCommentCount(article.getCommentCount() + 1L);
            commentMapper.incArticleCommentCount(article);
            //创建通知
            createNotify(comment, article.getCreator(), user.getName(), article.getTitle(), NotificationTypeEnum.REPLY_ARTICLE, article.getArticleId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long articleId) {
        if (receiver == comment.getCommentator()) {
            return;
        }
        Notification notification = new Notification();
        notification.setNotifier(comment.getCommentator());//消息发送人id
        notification.setNotifierName(notifierName);//消息发送人name
        notification.setOuterId(articleId);//文章id
        notification.setOuterTitle(outerTitle);//评论或文章的title
        notification.setType(notificationType.getType());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);//消息接收人
        notification.setGmtCreate(new Date());
        notificationMapper.insert(notification);
    }

    @Override
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum commentTypeEnum) {
        Integer type = commentTypeEnum.getType();
        List<Comment> commentList = commentMapper.findByIdOrType(id, type);
        if (commentList.size() == 0) {
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        //获取评论人并转换为Map
        List<User> users = userMapper.findByAllId(userIds);
        if (users.size() == 0) {
            return new ArrayList<>();
        }
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getUserId(), user -> user));
        //转换 comment  为 commentDTO
        List<CommentDTO> commentDTOList = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOList;
    }
}
