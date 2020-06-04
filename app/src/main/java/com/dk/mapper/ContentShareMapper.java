package com.dk.mapper;

import com.dk.entity.ContentShare;
import com.dk.mapper.base.IBaseDao;

public interface ContentShareMapper extends IBaseDao<ContentShare>{
    int deleteByPrimaryKey(Integer id);

    int insert(ContentShare record);

    int insertSelective(ContentShare record);

    ContentShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContentShare record);

    int updateByPrimaryKey(ContentShare record);
}