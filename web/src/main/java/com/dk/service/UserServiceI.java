package com.dk.service;

import com.dk.entity.User;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.dk.vo.UserVo;

public interface UserServiceI extends IBaseService<User>{

    PageResult<User> queryVoByPage(PageResult<User> page, UserVo user);
}
