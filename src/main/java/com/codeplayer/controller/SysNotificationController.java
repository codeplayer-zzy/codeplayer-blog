package com.codeplayer.controller;

import com.codeplayer.dto.NotificationDTO;
import com.codeplayer.entity.User;
import com.codeplayer.enums.NotificationTypeEnum;
import com.codeplayer.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SysNotificationController {
    @Autowired
    private NotificationService notificationService;

    /**
     * 未读变为已读功能
     */
    @GetMapping("/notification/{id}")
    public String readOrUnread(@PathVariable(name = "id") Long id, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_ARTICLE.getType().equals(notificationDTO.getType())
            || NotificationTypeEnum.REPLY_COMMENT.getType().equals(notificationDTO.getType())){
            return "redirect:/article/" + notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }
    }
}
