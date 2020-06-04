package com.dk.mapper;

import com.dk.entity.User;
import com.dk.entity.Vedio;
import com.dk.entity.VedioPoint;
import com.dk.mapper.base.IBaseDao;
import com.dk.vo.VedioPointVo;

import java.util.List;

public interface VedioPointMapper extends IBaseDao<VedioPoint>{
    int deleteByPrimaryKey(Integer id);

    int insert(VedioPoint record);

    int insertSelective(VedioPoint record);

    VedioPoint selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VedioPoint record);

    int updateByPrimaryKey(VedioPoint record);

    List<VedioPointVo> getVoPage(VedioPointVo vo);
}