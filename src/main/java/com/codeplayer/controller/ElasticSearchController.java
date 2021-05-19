package com.codeplayer.controller;

import com.codeplayer.entity.Article;
import com.codeplayer.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class ElasticSearchController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/search")
    public String search(String content, Model model) throws IOException {

        List<Article> articleList = articleService.searchFromEs(content);
        model.addAttribute("articleList",articleList);
        return "front/search";
    }
}
