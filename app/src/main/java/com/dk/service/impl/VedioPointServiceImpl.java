package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.VedioPoint;
import com.dk.mapper.UserMapper;
import com.dk.mapper.VedioMapper;
import com.dk.mapper.VedioPointMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.VedioPointServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class VedioPointServiceImpl extends BaseServiceImpl<VedioPoint> implements VedioPointServiceI {
    private Logger log = LoggerFactory.getLogger(VedioPointServiceImpl.class);
    @Autowired
    private VedioPointMapper vedioPointMapper;
    @Autowired
    private VedioMapper vedioMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    protected IBaseDao<VedioPoint> getMapper() {
        return vedioPointMapper;
    }

    @Override
    public void userPointVedio(Integer userId, Integer vedioId,Integer flag) {
        //用户点赞加1
        userMapper.addPointCount(userId);
        if(flag==1){
            VedioPoint point = new VedioPoint();
            point.setUserId(userId);
            point.setVedioId(vedioId);
            point.setCreateTime(new Date());
            vedioPointMapper.insertSelective(point);
            //视频点赞次数加1
            vedioMapper.addPointCount(vedioId);
        }else{
            //删除点赞记录
            vedioPointMapper.deletByParam(userId,vedioId);
            //视频点赞次数减1
            vedioMapper.subPointCount(vedioId);
        }
    }
}