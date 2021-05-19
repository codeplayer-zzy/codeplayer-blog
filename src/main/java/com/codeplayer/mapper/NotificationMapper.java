package com.codeplayer.mapper;

import com.codeplayer.entity.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: (notification)表数据库访问层
 * @author CodePlayer
 * @date 2021/4/6 20:26
 */
@Mapper
public interface NotificationMapper {
    @Select("SELECT count(1) FROM notification WHERE receiver = #{receiver} AND status = #{status}")
    Integer countByUserId(@Param("receiver") Long userId, @Param("status") Integer status);

    @Insert("INSERT INTO notification (notifier,notifier_name,outer_id,outer_title,type,status,receiver,gmt_create)" +
            "VALUES (#{notifier},#{notifierName},#{outerId},#{outerTitle},#{type},#{status},#{receiver},#{gmtCreate})")
    void insert(Notification notification);

    @Select("SELECT * FROM notification WHERE receiver = #{receiver} ORDER BY gmt_create DESC LIMIT #{offset},#{size}")
    List<Notification> profileRepliesPageList(@Param("receiver") Long userId, @Param("offset")Integer offset, @Param("size")Integer size);

    @Select("SELECT * FROM notification WHERE notification_id = #{notificationId}")
    Notification countByNotificationId(@Param("notificationId") Long id);

    @Update("UPDATE notification SET status = #{status} WHERE notification_id = #{notificationId}")
    void updateRead(Notification notification);
}
