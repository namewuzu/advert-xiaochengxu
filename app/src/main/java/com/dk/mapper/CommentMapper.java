package com.dk.mapper;

import com.dk.entity.Comment;
import com.dk.mapper.base.IBaseDao;
import com.dk.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends IBaseDao<Comment>{
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<CommentVo> getTwoRecord(@Param("vedioId")Integer vedioId);
}