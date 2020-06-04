package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Push;
import com.dk.entity.User;
import com.dk.mapper.PushMapper;
import com.dk.mapper.UserMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.PushServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.Constants;
import com.dk.util.RedisConstanct;
import com.dk.util.RedisPoolUtil;
import com.dk.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class PushServiceImpl extends BaseServiceImpl<Push> implements PushServiceI {
    private Logger log = LoggerFactory.getLogger(PushServiceImpl.class);
    @Autowired
    private PushMapper pushMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    protected IBaseDao<Push> getMapper() {
        return pushMapper;
    }

    @Override
    public void pushFormId(Push push) throws Exception {
        //根据userId获取openid
        User user = userMapper.selectByPrimaryKey(push.getUserId());
        if(user==null){
            return;
        }
        if(StringUtils.isNotBlank(user.getOpenId())){
            push.setOpenid(user.getOpenId());
        }
        Date now = new Date();
        push.setCreateTime(now);
        push.setTemplateId(Constants.TEMPT_ID);
        push.setUpdateTime(now);
        push.setFlag((byte) 1);
        pushMapper.insertSelective(push);
        //看是否是首次提交form
        /*if(push.getFormType()==6){
            user.setIsPush((byte) 1);
            userMapper.updateByPrimaryKeySelective(user);
        }*/
        //存放redis中
        //推送过期时间
        Integer secord = Integer.parseInt(RedisPoolUtil.getString(RedisConstanct.PUSH_TIME_SECORD));
        if(secord==null){
            secord = 518400;
        }
        RedisPoolUtil.put(Constants.PRE_PUSH+","+push.getFormId()+","+push.getOpenid()+","+push.getId(),push,secord);
    }
}