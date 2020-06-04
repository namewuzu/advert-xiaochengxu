package com.dk.mapper;

import com.dk.entity.Push;
import com.dk.mapper.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

public interface PushMapper extends IBaseDao<Push>{
    int deleteByPrimaryKey(Integer id);

    int insert(Push record);

    int insertSelective(Push record);

    Push selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Push record);

    int updateByPrimaryKey(Push record);

    Push selectByFormId(@Param("openid") String openid,@Param("today") String today);

    Integer selectCountByParam(@Param("openid") String openid,@Param("today") String today);
}