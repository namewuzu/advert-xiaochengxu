package com.dk.mapper;

import com.dk.entity.User;
import com.dk.mapper.base.IBaseDao;
import com.dk.vo.BaseVo;
import com.dk.vo.DataVo;
import com.dk.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper extends IBaseDao<User>{
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> getVoPage(@Param("user")UserVo user,@Param("sort")Map<String,Integer> sort);

    DataVo getData(BaseVo vo);
}