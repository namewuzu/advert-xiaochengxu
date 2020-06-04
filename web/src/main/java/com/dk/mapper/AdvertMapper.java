package com.dk.mapper;

import com.dk.entity.Advert;
import com.dk.mapper.base.IBaseDao;

import java.util.List;

public interface AdvertMapper extends IBaseDao<Advert>{
    int deleteByPrimaryKey(Integer id);

    int insert(Advert record);

    int insertSelective(Advert record);

    Advert selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advert record);

    int updateByPrimaryKeyWithBLOBs(Advert record);

    int updateByPrimaryKey(Advert record);

    List<Advert> selectAll();
}