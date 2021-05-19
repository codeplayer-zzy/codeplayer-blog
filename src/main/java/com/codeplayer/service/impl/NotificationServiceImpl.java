package com.codeplayer.service.impl;

import com.codeplayer.dto.NotificationDTO;
import com.codeplayer.dto.PageDTO;
import com.codeplayer.entity.Notification;
import com.codeplayer.entity.User;
import com.codeplayer.enums.NotificationStatusEnum;
import com.codeplayer.enums.NotificationTypeEnum;
import com.codeplayer.exception.CustomizeErrorCode;
import com.codeplayer.exception.CustomizeException;
import com.codeplayer.service.BaseService;
import com.codeplayer.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("notificationService")
public class NotificationServiceImpl extends BaseService implements NotificationService {
    @Override
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.countByNotificationId(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(),user.getUserId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //设置已读
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateRead(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }

    @Override
    public Integer unreadCount(Long userId) {
        return notificationMapper.countByUserId(userId,NotificationStatusEnum.UNREAD.getStatus());
    }

    @Override
    public PageDTO<NotificationDTO> profileRepliesPageList(Long userId, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();
        Integer totalPage;
        Integer totalCount = notificationMapper.countByUserId(userId, NotificationStatusEnum.UNREAD.getStatus());
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

        Integer offset = size * (page - 1);
        List<Notification> notifications = notificationMapper.profileRepliesPageList(userId,offset, size);

        if (notifications.size() == 0){
             return pageDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        pageDTO.setData(notificationDTOS);
        pageDTO.setPagination(totalPage, page);
        return pageDTO;
    }
}
