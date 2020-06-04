package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.mapper.UserMapper;
import com.dk.service.DataServiceI;
import com.dk.vo.BaseVo;
import com.dk.vo.DataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class DataServiceImpl  implements DataServiceI {
    private Logger log = LoggerFactory.getLogger(DataServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    public DataVo getData(BaseVo vo) {
        return userMapper.getData(vo);
    }
}