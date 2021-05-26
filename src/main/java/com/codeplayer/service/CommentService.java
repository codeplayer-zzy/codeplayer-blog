package com.codeplayer.service;

import com.codeplayer.dto.CommentDTO;
import com.codeplayer.entity.Comment;
import com.codeplayer.entity.User;
import com.codeplayer.enums.CommentTypeEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    void insert(Comment comment, User user);

    List<CommentDTO> listByTargetId(Long id, CommentTypeEnum commentTypeEnum);

    Integer delCommentsByCommentId(Long id);

    Integer likeCommentsByCommentId(Long id);

    Integer cancelLikeCommentByCommentId(Long id);

}
