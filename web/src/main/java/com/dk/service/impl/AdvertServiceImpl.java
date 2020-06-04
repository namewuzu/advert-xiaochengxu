package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Advert;
import com.dk.mapper.AdvertMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.AdvertServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class AdvertServiceImpl extends BaseServiceImpl<Advert> implements AdvertServiceI {
    private Logger log = LoggerFactory.getLogger(AdvertServiceImpl.class);
    @Autowired
    private AdvertMapper advertMapper;

    @Override
    protected IBaseDao<Advert> getMapper() {
        return advertMapper;
    }

    @Override
    public List<Advert> selectAll() {
        return advertMapper.selectAll();
    }
}