package com.dk.mapper;

import com.dk.entity.ShareWeight;
import com.dk.mapper.base.IBaseDao;

import java.util.List;

public interface ShareWeightMapper extends IBaseDao<ShareWeight>{
    int deleteByPrimaryKey(Integer id);

    int insert(ShareWeight record);

    int insertSelective(ShareWeight record);

    ShareWeight selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShareWeight record);

    int updateByPrimaryKey(ShareWeight record);

    List<ShareWeight> selectAll();
}