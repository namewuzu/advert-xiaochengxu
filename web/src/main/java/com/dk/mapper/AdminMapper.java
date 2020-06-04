package com.dk.mapper;

import com.dk.entity.Admin;
import com.dk.mapper.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper extends IBaseDao<Admin>{
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin getUserInfo(@Param("account") String username);
}