package com.dk.mapper;

import com.dk.entity.VedioPoint;
import com.dk.mapper.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

public interface VedioPointMapper extends IBaseDao<VedioPoint>{
    int deleteByPrimaryKey(Integer id);

    int insert(VedioPoint record);

    int insertSelective(VedioPoint record);

    VedioPoint selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VedioPoint record);

    int updateByPrimaryKey(VedioPoint record);

    void deletByParam(@Param("userId")Integer userId,@Param("vedioId") Integer vedioId);
}