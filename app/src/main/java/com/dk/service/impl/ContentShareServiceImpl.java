package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.ContentShare;
import com.dk.mapper.ContentShareMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.ContentShareServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class ContentShareServiceImpl extends BaseServiceImpl<ContentShare> implements ContentShareServiceI {
    private Logger log = LoggerFactory.getLogger(ContentShareServiceImpl.class);
    @Autowired
    private ContentShareMapper contentShareMapper;

    @Override
    protected IBaseDao<ContentShare> getMapper() {
        return contentShareMapper;
    }

}