package com.dk.mapper;

import com.dk.entity.Dict;
import org.apache.ibatis.annotations.Param;

public interface DictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

    String selectValueByKey(@Param("key") int key);
}