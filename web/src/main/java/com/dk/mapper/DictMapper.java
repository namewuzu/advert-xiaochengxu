package com.dk.mapper;

import com.dk.entity.Dict;
import com.dk.mapper.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictMapper extends IBaseDao<Dict>{
    int deleteByPrimaryKey(Integer id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

    List<Dict> selectAll();

    Dict selectBySkey(@Param("skey") Byte i);
}