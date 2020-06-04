package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.dk.entity.User;
import com.dk.mapper.DictMapper;
import com.dk.mapper.UserMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.UserServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.Constants;
import com.dk.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserServiceI {
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DictMapper dictMapper;

    @Override
    protected IBaseDao<User> getMapper() {
        return userMapper;
    }

    @Override
    public synchronized User saveUserInfor(User user) throws Exception{
        userMapper.updateByPrimaryKeySelective(user);
        return user;
    }

    @Override
    public User parseUser(JSONObject userInforJson, String openId) throws Exception {
        User user = null;
        if(userInforJson!=null){
            user = new User();
            user.setOpenId(openId);
            user.setNickName( new String(String.valueOf(userInforJson.get("nickname")).getBytes("ISO8859-1"),"UTF-8"));
            user.setAvatarUrl(userInforJson.getString("headimgurl"));
            user.setSex(userInforJson.getByte("sex"));
            saveUserInfor(user);
        }
        return user;
    }

    @Override
    public synchronized User loginUser(String code) {
        String openId=null;
        JSONObject openIdJson = HttpRequestUtil.httpGet(Constants.GET_OPENID_URL.replace("APPID",Constants.WX_APP_ID).replace
                ("SECRET",Constants.WX_APP_SECRET).replace("CODE",code));
        if(openIdJson!=null){
            openId = openIdJson.getString("openid");
            User user = new User();
            user.setOpenId(openId);
            user.setCreateTime(new Date());
            User userEx = userMapper.selectByParam(user);
            if(userEx==null){
                userMapper.insertSelective(user);
                return user;
            }else{
                return userEx;
            }
        }
        return null;
    }

    @Override
    public String getPageFlag() {
        return dictMapper.selectValueByKey(2);
    }
}