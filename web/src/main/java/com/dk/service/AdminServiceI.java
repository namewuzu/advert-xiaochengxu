package com.dk.service;

import com.dk.entity.Admin;
import com.dk.entity.User;
import com.dk.service.base.IBaseService;

public interface AdminServiceI extends IBaseService<Admin>{

    Admin getUserInfo(String username);
}
