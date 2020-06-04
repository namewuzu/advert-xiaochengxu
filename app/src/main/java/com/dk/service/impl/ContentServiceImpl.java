package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Content;
import com.dk.mapper.ContentMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.ContentServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentServiceI {
    private Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);
    @Autowired
    private ContentMapper contentMapper;

    @Override
    protected IBaseDao<Content> getMapper() {
        return contentMapper;
    }

    @Override
    public Integer getCountByType(Byte typeFlag) {
        return contentMapper.getCountByType(typeFlag);
    }

    @Override
    public List<Content> getRandomPageByType(Integer countRondom, Integer pageSize, Byte typeFlag) {
        return contentMapper.getRandomPageByType(countRondom,pageSize,typeFlag);
    }
}