package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Admin;
import com.dk.entity.User;
import com.dk.mapper.AdminMapper;
import com.dk.mapper.UserMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.AdminServiceI;
import com.dk.service.UserServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminServiceI {
    private Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected IBaseDao<Admin> getMapper() {
        return adminMapper;
    }

    @Override
    public Admin getUserInfo(String username) {
        return adminMapper.getUserInfo(username);
    }
}