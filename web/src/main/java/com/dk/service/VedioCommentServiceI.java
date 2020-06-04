package com.dk.service;

import com.dk.entity.Comment;
import com.dk.entity.VedioPlay;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.dk.vo.VedioCommentVo;
import com.dk.vo.VedioPlayVo;

public interface VedioCommentServiceI extends IBaseService<Comment>{

    PageResult<VedioCommentVo> queryVoByPage(PageResult<VedioCommentVo> page, VedioCommentVo vo);

}
