package com.codeplayer.controller;

import com.codeplayer.cache.HotTagCache;
import com.codeplayer.dto.ArticleDTO;
import com.codeplayer.dto.PageDTO;
import com.codeplayer.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SysIndexController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private HotTagCache hotTagCache;

    /**
     * 主页面
     */
    @GetMapping({"/","/index"})
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "sort", required = false) String sort
                        ){
        PageDTO<ArticleDTO> pageDTO = articleService.articlePageList(page,size,tag,sort);
        List<String> hotTags = hotTagCache.getHots();
        model.addAttribute("hotTags",hotTags);
        model.addAttribute("tag",tag);
        model.addAttribute("sort",sort);
        model.addAttribute("pageDTO",pageDTO);
        return "front/index";
    }
}
