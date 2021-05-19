package com.codeplayer.controller;

import com.codeplayer.dto.ArticleDTO;
import com.codeplayer.dto.NotificationDTO;
import com.codeplayer.dto.PageDTO;
import com.codeplayer.entity.User;
import com.codeplayer.service.ArticleService;
import com.codeplayer.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SysProfileController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private NotificationService notificationService;

    //profile页面跳转
    @GetMapping("/profile/{active}")
    public String profile(@PathVariable(name = "active")String active,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "3") Integer size,
                          HttpServletRequest request,
                          Model model){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }

        if ("article".equals(active)){
            model.addAttribute("section","article");
            model.addAttribute("sectionName","我的文章");
            PageDTO<ArticleDTO> profileArticlePageDTO = articleService.profileArticlePageList(user.getUserId(), page, size);
            model.addAttribute("profilePageDTO",profileArticlePageDTO);
        }else if("replies".equals(active)) {
            PageDTO<NotificationDTO> profileRepliesPageDTO = notificationService.profileRepliesPageList(user.getUserId(), page, size);
            model.addAttribute("profilePageDTO", profileRepliesPageDTO);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的回复");
        }
        return "front/profile";
    }
}
