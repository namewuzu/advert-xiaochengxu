package com.dk.service.impl;

import com.dk.mapper.base.IBaseDao;
import com.dk.mapper.user.UserMapper;
import com.dk.service.UserServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuzu on 2018/3/17.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserServiceI {
    @Autowired
    private UserMapper userMapper;
    @Override
    protected IBaseDao<User> getMapper() {
        return userMapper;
    }

}
