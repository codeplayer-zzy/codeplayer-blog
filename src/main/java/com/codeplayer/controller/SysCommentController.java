package com.codeplayer.controller;

import com.codeplayer.dto.CommentDTO;
import com.codeplayer.dto.CommentResultDTO;
import com.codeplayer.dto.ResultDTO;
import com.codeplayer.entity.Comment;
import com.codeplayer.entity.User;
import com.codeplayer.enums.CommentTypeEnum;
import com.codeplayer.exception.CustomizeErrorCode;
import com.codeplayer.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@Controller
public class SysCommentController {

    @Autowired
    private CommentService commentService;
    private static final Logger log = LoggerFactory.getLogger(SysCommentController.class);

    /**
     *
     * @param commentResultDTO
     * @param request
     * 创建回复
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentResultDTO commentResultDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentResultDTO == null || StringUtils.isBlank(commentResultDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentResultDTO.getParentId());
        comment.setType(commentResultDTO.getType());
        comment.setContent(commentResultDTO.getContent());
        comment.setGmtCreate(new Date());
        comment.setGmtModified(new Date());
        comment.setCommentator(user.getUserId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0L);
        commentService.insert(comment, user);
        return ResultDTO.okOf();
    }

    /**
     * 查找回复
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/delComment")
    public ResultDTO delComments(@RequestParam(value = "id") Long id) {
        Integer aa = commentService.delCommentsByCommentId(id);
        if (aa == 0) {
            return ResultDTO.errorOf(100,"服务冒烟了，要不然你稍后再试试！！");
        }else {
            return ResultDTO.okOf(200,"恭喜您，删除成功了！！");
        }
    }
}
