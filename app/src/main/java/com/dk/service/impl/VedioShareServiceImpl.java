package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Vedio;
import com.dk.entity.VedioShare;
import com.dk.mapper.UserMapper;
import com.dk.mapper.VedioMapper;
import com.dk.mapper.VedioShareMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.VedioShareServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.DateUtils;
import com.mysql.fabric.xmlrpc.base.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class VedioShareServiceImpl extends BaseServiceImpl<VedioShare> implements VedioShareServiceI {
    private Logger log = LoggerFactory.getLogger(VedioShareServiceImpl.class);
    @Autowired
    private VedioShareMapper vedioShareMapper;
    @Autowired
    private VedioMapper vedioMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    protected IBaseDao<VedioShare> getMapper() {
        return vedioShareMapper;
    }

    @Override
    public void userShareVedio(Integer userId, Integer vedioId,Byte shareType)throws Exception {
        VedioShare share = new VedioShare();
        share.setCreateTime(new Date());
        share.setUserId(userId);
        share.setVedioId(vedioId);
        share.setShareType(shareType);
        vedioShareMapper.insertSelective(share);
        //总分享次数，今天分享次数处理逻辑
        dealTodayCount(vedioId);
        //用户分享加1
        userMapper.addShareCount(userId);
    }

    /**
     * 今天视频分享次数++
     * @param vedioId
     */
    public void dealTodayCount(Integer vedioId) {
        Vedio vedio = vedioMapper.selectByPrimaryKey(vedioId);
        if(vedio==null){
            return;
        }
        Date oldDay = vedio.getToday();
        Date today = DateUtils.getToday();
        //昨天旧的数据
        if(oldDay==null || oldDay.getTime()-today.getTime()<0){
            vedio.setTodayCount(1);
            vedio.setToday(today);
            //总次数++
            vedio.setShareCount(vedio.getShareCount()+1);
            vedioMapper.updateByPrimaryKeySelective(vedio);
        }else{
            //今天分享次数++ 总次数++
            vedioMapper.addAllShareCount(vedioId);
        }
    }
}