package com.dk.mapper;

import com.dk.entity.VedioCollect;
import com.dk.mapper.base.IBaseDao;
import com.dk.vo.VedioCollectVo;

import java.util.List;

public interface VedioCollectMapper extends IBaseDao<VedioCollect>{
    int deleteByPrimaryKey(Integer id);

    int insert(VedioCollect record);

    int insertSelective(VedioCollect record);

    VedioCollect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VedioCollect record);

    int updateByPrimaryKey(VedioCollect record);

    List<VedioCollectVo> getVoPage(VedioCollectVo vo);
}