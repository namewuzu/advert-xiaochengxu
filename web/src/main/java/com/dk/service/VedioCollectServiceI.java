package com.dk.service;

import com.dk.entity.VedioCollect;
import com.dk.entity.VedioPlay;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.dk.vo.VedioCollectVo;
import com.dk.vo.VedioPlayVo;

public interface VedioCollectServiceI extends IBaseService<VedioCollect>{

    PageResult<VedioCollectVo> queryVoByPage(PageResult<VedioCollectVo> page, VedioCollectVo vo);

}
