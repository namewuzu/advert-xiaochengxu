package com.dk.service;

import com.dk.entity.VedioShare;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.dk.vo.VedioShareVo;

public interface VedioShareServiceI extends IBaseService<VedioShare>{

    PageResult<VedioShareVo> queryVoByPage(PageResult<VedioShareVo> page, VedioShareVo vo);

}
