package com.codeplayer.controller;

import com.codeplayer.dto.ArticleDTO;
import com.codeplayer.dto.CommentDTO;
import com.codeplayer.enums.CommentTypeEnum;
import com.codeplayer.service.ArticleService;
import com.codeplayer.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PageArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    /**
     * 跳转到文章页面
     */
    @GetMapping("/article/{id}")
    public String article(@PathVariable(name = "id") Long id,
            ModelMap model){
        //查找文章内容
        ArticleDTO articleDTO = articleService.getById(id);
        //查找评论
        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.ARTICLE);
        //查找相关文章
        List<ArticleDTO> articleDTOList = articleService.selectRelated(articleDTO);
        //增加阅读数
        articleService.incView(id);
        model.addAttribute("articleDTO", articleDTO);
        model.addAttribute("commentDTOList", commentDTOList);
        model.addAttribute("articleDTOList", articleDTOList);
        return "page/article";
    }

}
