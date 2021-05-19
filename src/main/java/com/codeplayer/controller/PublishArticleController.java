package com.codeplayer.controller;

import com.codeplayer.cache.TagCache;
import com.codeplayer.dto.ArticleDTO;
import com.codeplayer.entity.Article;
import com.codeplayer.entity.User;
import com.codeplayer.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@Controller
//@RequestMapping("/publish")
public class PublishArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(PublishArticleController.class);

    /**
     * 编辑文章
     */
    @GetMapping("/publish/{id}")
    public String editArticle(@PathVariable(name = "id") Long id,
                              Model model){
        ArticleDTO article = articleService.getById(id);
        model.addAttribute("title", article.getTitle());
        model.addAttribute("content", article.getContent());
        model.addAttribute("tag", article.getTag());
        model.addAttribute("articleId", article.getArticleId());
        return "publish/pharticle";
    }

    /**
     * 跳转发布页面
     */
    @GetMapping("/particle")
    public String publish(ModelMap model){
        model.addAttribute("tags", TagCache.get());
        return "publish/pharticle";
    }

    /**
     * 发布功能
     */
    @PostMapping("/particle")
    @Transactional
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "articleId", required = false) Long articleId,
            HttpServletRequest request,
            Model model){
        model.addAttribute("tags", TagCache.get());
        model.addAttribute("content", content);
        model.addAttribute("title", title);
        model.addAttribute("tag", tag);

        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish/pharticle";
        }
        if (content == null || content == ""){
            model.addAttribute("error","问题补充不足");
            return "publish/pharticle";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish/pharticle";
        }

        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error", "输入非法标签：" + invalid);
            return "publish/pharticle";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error","用户未登录");
            return "login";
        }
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setTag(tag);
        article.setArticleId(articleId);
        article.setCreator(user.getUserId());
        articleService.createOrUpdate(article);
        if (articleId == null) {
            //消费生产者
            //发布新的文章，找到当前文章id
            List<Article> articleList = articleService.findAllArticle();
            Optional<Article> article1 = articleList.stream().findFirst();
            Long articleId1 = article1.get().getArticleId();
            rabbitTemplate.convertAndSend("es", "article.save", articleId1);
            return "redirect:/";
        }
        //消费生产者
        //发布更新的文章
        rabbitTemplate.convertAndSend("es", "article.save", articleId);
        return "redirect:/";
    }
}
