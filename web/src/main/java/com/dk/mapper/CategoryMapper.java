package com.dk.mapper;

import com.dk.entity.Category;
import com.dk.mapper.base.IBaseDao;

import java.util.List;

public interface CategoryMapper extends IBaseDao<Category> {
    int deleteByPrimaryKey(String id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectAll();
}