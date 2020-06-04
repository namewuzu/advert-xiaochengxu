package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Dict;
import com.dk.entity.ShareWeight;
import com.dk.mapper.DictMapper;
import com.dk.mapper.ShareWeightMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.DictServiceI;
import com.dk.service.ShareWeightServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class ShareWeightServiceImpl extends BaseServiceImpl<ShareWeight> implements ShareWeightServiceI {
    private Logger log = LoggerFactory.getLogger(ShareWeightServiceImpl.class);
    @Autowired
    private ShareWeightMapper shareWeightMapper;

    @Override
    protected IBaseDao<ShareWeight> getMapper() {
        return shareWeightMapper;
    }

    @Override
    public List<ShareWeight> selectAll() {
        return shareWeightMapper.selectAll();
    }
}