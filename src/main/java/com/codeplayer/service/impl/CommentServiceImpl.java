package com.codeplayer.service.impl;

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
    /**
     *  插入评论
     * @param comment
     * @param user
     */
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
            //回复的评论
            Comment dbcomment = commentMapper.findByCommentId(comment.getParentId());
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
            commentMapper.incCommentCommentCount(comment1);
            //创建通知
            createNotify(comment, dbcomment.getCommentator(), user.getName(), article.getTitle(), NotificationTypeEnum.REPLY_COMMENT,article.getArticleId());
        }else {
            //回复的文章
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

    /**
     *  创建通知
     * @param comment
     * @param receiver
     * @param notifierName
     * @param outerTitle
     * @param notificationType
     * @param articleId
     */
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

    /**
     *  查找评论
     * @param id
     * @param commentTypeEnum
     * @return
     */
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

    /**
     *  删除文章的评论
     * @param id
     * @return
     */
    @Override
    public Integer delCommentsByCommentId(Long id) {
        Comment comment = commentMapper.findByCommentId(id);
        Long commentId = comment.getCommentId();
        commentMapper.deleteByParentId(commentId);//删除二级评论
        Integer aa = commentMapper.deleteByCommentId(id);//删除一级评论
        return aa;//成功与否
    }

    /**
     *  增加点赞数
     * @param id
     * @return
     */
    @Override
    public Integer likeCommentsByCommentId(Long id) {
        Comment comment = commentMapper.findByCommentId(id);
        comment.setLikeCount(comment.getLikeCount() + 1L);
        Integer aa = commentMapper.CommentLikeCount(comment);
        return aa;
    }

    /**
     *  取消点赞评论
     * @param id
     * @return
     */
    @Override
    public Integer cancelLikeCommentByCommentId(Long id) {
        Comment comment = commentMapper.findByCommentId(id);
        comment.setLikeCount(comment.getLikeCount() - 1L);
        Integer aa = commentMapper.CommentLikeCount(comment);
        return aa;
    }

}
