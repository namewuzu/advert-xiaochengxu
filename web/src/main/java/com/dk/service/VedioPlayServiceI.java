package com.dk.service;

import com.dk.entity.VedioPlay;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.dk.vo.VedioPlayVo;

public interface VedioPlayServiceI extends IBaseService<VedioPlay>{

    PageResult<VedioPlayVo> queryVoByPage(PageResult<VedioPlayVo> page, VedioPlayVo vo);

}
