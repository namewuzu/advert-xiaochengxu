package com.dk.service;

import com.dk.entity.VedioPoint;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.dk.vo.VedioPointVo;

public interface VedioPointServiceI extends IBaseService<VedioPoint>{

    PageResult<VedioPointVo> queryVoByPage(PageResult<VedioPointVo> page, VedioPointVo vo);

}
