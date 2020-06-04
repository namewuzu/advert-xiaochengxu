package com.dk.service;

import com.dk.entity.Vedio;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.dk.vo.VedioInforVo;
import com.dk.vo.VedioTypeVo;
import com.dk.vo.VedioVo;

import java.util.List;

public interface VedioServiceI extends IBaseService<Vedio>{

    PageResult getVoPage(PageResult page, VedioVo vo)throws Exception;

    List<Vedio> getRandomVedios()throws Exception;

    VedioInforVo getVedioById(Integer vedioId,Integer userId)throws Exception;

    PageResult queryTypeByPage(PageResult<VedioTypeVo> page, VedioTypeVo vedioTypeVo)throws Exception;

    PageResult getRandomVoPage(PageResult page, VedioVo vo);

    void addCommentCount(Integer vedioId);
}
