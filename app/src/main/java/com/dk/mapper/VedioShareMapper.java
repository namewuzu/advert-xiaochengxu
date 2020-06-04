package com.dk.mapper;

import com.dk.entity.VedioShare;
import com.dk.mapper.base.IBaseDao;

public interface VedioShareMapper extends IBaseDao<VedioShare>{
    int deleteByPrimaryKey(Integer id);

    int insert(VedioShare record);

    int insertSelective(VedioShare record);

    VedioShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VedioShare record);

    int updateByPrimaryKey(VedioShare record);
}