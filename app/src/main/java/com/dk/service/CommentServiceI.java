package com.dk.service;

import com.dk.entity.Comment;
import com.dk.service.base.IBaseService;
import com.dk.vo.CommentVo;

import java.util.List;

public interface CommentServiceI extends IBaseService<Comment>{

    List<CommentVo> getTwoRecord(Integer vedioId);

    void commentVedio(Comment comment);
}
