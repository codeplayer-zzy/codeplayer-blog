package com.codeplayer.controller;

import com.codeplayer.dto.ArticleDTO;
import com.codeplayer.dto.CommentDTO;
import com.codeplayer.dto.ResultDTO;
import com.codeplayer.elasticsearch.ArticleRepository;
import com.codeplayer.enums.CommentTypeEnum;
import com.codeplayer.service.ArticleService;
import com.codeplayer.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PageArticleController {
    @Autowired
    private ArticleService articleService;

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleRepository articleRepository;

    private static final Logger log = LoggerFactory.getLogger(PageArticleController.class);

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

    /**
     *  回收，放进草稿箱
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/delArticle")
    public ResultDTO delArticle(@RequestParam(value = "id") Long id){
        Integer aa = articleService.delArticleByArticleId(id);
        if (aa == 0) {
            return ResultDTO.errorOf(100, "服务冒烟了，要不然你稍后再试试！！");
        }else {
            //rabbitTemplate.convertAndSend("es","article.delete", id);
            //删除ES索引数据
            articleRepository.deleteById(id);
            log.warn("【Elasticsearch】消费成功,删除ES索引数据");
            return ResultDTO.okOf(200,"恭喜您，轻删除成功了！！");
        }
    }

    /**
     *  永久删除
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/deleteArticle")
    public ResultDTO deleteArticle(@RequestParam(value = "id") Long id) {
        Integer aa = articleService.deleteArticleByArticleId(id);
        if (aa == 0) {
            return ResultDTO.errorOf(100, "服务冒烟了，要不然你稍后再试试！！");
        }else {
            return ResultDTO.okOf(200,"恭喜您，永久删除成功了！！");
        }
    }




}
