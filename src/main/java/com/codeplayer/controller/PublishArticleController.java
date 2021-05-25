package com.codeplayer.controller;

import com.codeplayer.cache.TagCache;
import com.codeplayer.dto.ArticleDTO;
import com.codeplayer.dto.ResultDTO;
import com.codeplayer.entity.Article;
import com.codeplayer.entity.User;
import com.codeplayer.enums.ArticleStatusEnum;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
    @ResponseBody
    @GetMapping("/publishArticle")
    @Transactional
    public ResultDTO doPublish(
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

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error","用户未登录");
            return ResultDTO.errorOf(101,"用户未登录");
        }

        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return ResultDTO.errorOf(100,"标题不能为空");
        }

        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return ResultDTO.errorOf(100,"标签不能为空");
        }

        if (content == null || content == ""){
            model.addAttribute("error","问题补充不足");
            return ResultDTO.errorOf(100,"问题补充不足");
        }

        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error", "输入非法标签：" + invalid);
            return ResultDTO.errorOf(100,"输入非法标签");
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
            List<Article> articleList = articleService.findByPublishStatus(ArticleStatusEnum.PUBLISHED.getStatus());
            Optional<Article> article1 = articleList.stream().findFirst();
            Long articleId1 = article1.get().getArticleId();
            rabbitTemplate.convertAndSend("es", "article.save", articleId1);
            return ResultDTO.okOf(200,"发布文章成功！！");
        }
        //消费生产者
        //发布更新的文章
        rabbitTemplate.convertAndSend("es", "article.save", articleId);
        return ResultDTO.okOf(200, "更新文章成功！！");
    }

    /**
     *  保存文章
     * @param title
     * @param content
     * @param tag
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @GetMapping("/saveArticle")
    public ResultDTO saveArticle(@RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "content", required = false) String content,
                              @RequestParam(value = "tag", required = false) String tag,
                              HttpServletRequest request,
                              Model model) {
        model.addAttribute("tags", TagCache.get());
        model.addAttribute("content", content);
        model.addAttribute("title", title);
        model.addAttribute("tag", tag);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error","用户未登录");
            return ResultDTO.errorOf(101,"用户未登录");
        }
        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return ResultDTO.errorOf(100,"标题不能为空");
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return ResultDTO.errorOf(100,"标签不能为空");
        }
        if (content == null || content == ""){
            model.addAttribute("error","问题补充不足");
            return ResultDTO.errorOf(100,"问题补充不足");
        }

        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error", "输入非法标签：" + invalid);
            return ResultDTO.errorOf(100,"输入非法标签");
        }

        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setTag(tag);
        article.setCreator(user.getUserId());
        article.setStatus(ArticleStatusEnum.DRAFT.getStatus());
        article.setGmtCreate(new Date());
        article.setGmtModified(new Date());
        article.setLikeCount(0L);
        article.setViewCount(0L);
        article.setCommentCount(0L);
        Integer aa = articleService.saveArticle(article);
        if (aa == 0) {
            return ResultDTO.errorOf(100, "服务冒烟了，要不然你稍后再试试！！");
        }else {
            return ResultDTO.okOf(201,"恭喜您，已保存到草稿箱里！！");
        }
    }
}
