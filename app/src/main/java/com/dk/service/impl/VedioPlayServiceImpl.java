package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.VedioPlay;
import com.dk.entity.VedioPoint;
import com.dk.mapper.UserMapper;
import com.dk.mapper.VedioMapper;
import com.dk.mapper.VedioPlayMapper;
import com.dk.mapper.VedioPointMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.VedioPlayServiceI;
import com.dk.service.VedioPointServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class VedioPlayServiceImpl extends BaseServiceImpl<VedioPlay> implements VedioPlayServiceI {
    private Logger log = LoggerFactory.getLogger(VedioPlayServiceImpl.class);
    @Autowired
    private VedioPlayMapper vedioPlayMapper;
    @Autowired
    private VedioMapper vedioMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    protected IBaseDao<VedioPlay> getMapper() {
        return vedioPlayMapper;
    }

    @Override
    public void userPlayVedio(Integer userId, Integer vedioId) throws Exception{
        VedioPlay play = new VedioPlay();
        play.setCreateTime(new Date());
        play.setUserId(userId);
        play.setVedioId(vedioId);
        vedioPlayMapper.insertSelective(play);
        //播放次数加
        vedioMapper.addPlayCount(vedioId);
        //用户播放加1
        userMapper.addPlayCount(userId);
    }
}