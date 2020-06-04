package com.dk.service;

import com.alibaba.fastjson.JSONObject;
import com.dk.entity.User;
import com.dk.service.base.IBaseService;

public interface UserServiceI extends IBaseService<User>{

     User saveUserInfor(User user)throws Exception;

     User parseUser(JSONObject userInforJson, String openId) throws Exception;

     User loginUser(String code);

     String getPageFlag();
}
