package com.dk.mapper;

import com.dk.entity.VedioShare;
import com.dk.mapper.base.IBaseDao;
import com.dk.vo.VedioShareVo;

import java.util.List;

public interface VedioShareMapper extends IBaseDao<VedioShare>{
    int deleteByPrimaryKey(Integer id);

    int insert(VedioShare record);

    int insertSelective(VedioShare record);

    VedioShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VedioShare record);

    int updateByPrimaryKey(VedioShare record);

    List<VedioShareVo> getVoPage(VedioShareVo vo);
}