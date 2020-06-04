package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Dict;
import com.dk.mapper.DictMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.DictServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.RedisConstanct;

import java.util.List;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictServiceI {
    private Logger log = LoggerFactory.getLogger(DictServiceImpl.class);
    @Autowired
    private DictMapper dictMapper;

    @Override
    protected IBaseDao<Dict> getMapper() {
        return dictMapper;
    }

    @Override
    public List<Dict> selectAll() {
        return dictMapper.selectAll();
    }

    @Override
    public void updateDbAndRedis(Dict dict) {
        dictMapper.updateByPrimaryKeySelective(dict);
        if(dict.getSkey()==1){
            //分享量为多少做随机推荐，redis 更新
            RedisPoolUtil.putString(RedisConstanct.SHARE_WEIGHT_TUI,dict.getVal());;
        }
        if(dict.getSkey()==3){
            //消息推送分享量参数（推送不小于该值的视频），redis 更新
            RedisPoolUtil.putString(RedisConstanct.PUSH_SHARE_COUNT,dict.getVal());;
        }
        if(dict.getSkey()==4){
            //推送定时时间（单位秒），redis 更新
            RedisPoolUtil.putString(RedisConstanct.PUSH_TIME_SECORD,dict.getVal());;
        }
        if(dict.getSkey()==6){
            //推荐类目播放量最低值，redis 更新
            RedisPoolUtil.putString(RedisConstanct.RECOMMEND_PLAY_COUNT,dict.getVal());;
        }
    }
}