package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.VedioCollect;
import com.dk.mapper.UserMapper;
import com.dk.mapper.VedioCollectMapper;
import com.dk.mapper.VedioMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.CollectServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class CollectServiceImpl extends BaseServiceImpl<VedioCollect> implements CollectServiceI {
    private Logger log = LoggerFactory.getLogger(CollectServiceImpl.class);
    @Autowired
    private VedioCollectMapper vedioCollectMapper;
    @Autowired
    private VedioMapper vedioMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    protected IBaseDao<VedioCollect> getMapper() {
        return vedioCollectMapper;
    }

    @Override
    public void userCollect(Integer userId, Integer vedioId)  throws Exception{
        VedioCollect collect = new VedioCollect();
        collect.setCreateTime(new Date());
        collect.setUserId(userId);
        collect.setVedioId(vedioId);
        collect.setUpdateTime(new Date());
        vedioCollectMapper.insertSelective(collect);
        //视频收藏总数++
        vedioMapper.addCollectCount(vedioId);
        //用户收藏加1
        userMapper.addCollectCount(userId);
    }
}