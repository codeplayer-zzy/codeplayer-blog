package com.codeplayer.service;

import com.codeplayer.dto.NotificationDTO;
import com.codeplayer.dto.PageDTO;
import com.codeplayer.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    PageDTO<NotificationDTO> profileRepliesPageList(Long userId, Integer page, Integer size);

    Integer unreadCount(Long userId);

    NotificationDTO read(Long id, User user);
}
