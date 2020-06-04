package com.dk.mapper;

import com.dk.entity.Content;
import com.dk.mapper.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentMapper extends IBaseDao<Content>{
    int deleteByPrimaryKey(Integer id);

    int insert(Content record);

    int insertSelective(Content record);

    Content selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Content record);

    int updateByPrimaryKeyWithBLOBs(Content record);

    int updateByPrimaryKey(Content record);

    Integer getCountByType(@Param("typeFlag") Byte typeFlag);

    List<Content> getRandomPageByType(@Param("countRondom")Integer countRondom, @Param("pageSize")
            Integer pageSize,@Param("typeFlag") Byte typeFlag);
}