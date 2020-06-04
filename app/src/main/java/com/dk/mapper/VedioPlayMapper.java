package com.dk.mapper;

import com.dk.entity.VedioPlay;
import com.dk.mapper.base.IBaseDao;

public interface VedioPlayMapper extends IBaseDao<VedioPlay>{
    int deleteByPrimaryKey(Integer id);

    int insert(VedioPlay record);

    int insertSelective(VedioPlay record);

    VedioPlay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VedioPlay record);

    int updateByPrimaryKey(VedioPlay record);
}