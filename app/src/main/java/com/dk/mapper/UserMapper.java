package com.dk.mapper;

import com.dk.entity.User;
import com.dk.mapper.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends IBaseDao<User>{
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByParam(User user);

    void addPointCount(@Param("userId")Integer vedioId);

    void addPlayCount(@Param("userId")Integer vedioId);

    void addShareCount(@Param("userId")Integer vedioId);

    void addCollectCount(@Param("userId")Integer vedioId);

    void addCommentCount(@Param("userId") Integer vedioId);
}