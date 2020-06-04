package com.dk.service;

import com.dk.entity.Comment;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.dk.vo.CommentVo;

public interface CommentServiceI extends IBaseService<Comment>{

    PageResult<CommentVo> queryVoByPage(PageResult<CommentVo> page, CommentVo commentVo);
}
