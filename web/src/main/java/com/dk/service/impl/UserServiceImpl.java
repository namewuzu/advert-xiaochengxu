package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.User;
import com.dk.mapper.UserMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.UserServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.PageResult;
import com.dk.vo.UserVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserServiceI {
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    protected IBaseDao<User> getMapper() {
        return userMapper;
    }

    @Override
    public PageResult<User> queryVoByPage(PageResult<User> page, UserVo user) {
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getVoPage(user),page);
    }

    public List<User> getVoPage(UserVo vedio) {
        //排序字段处理
        Map<String,Integer> sort = new HashMap<>();
        if(vedio.getShareFlag()!=null){
            sort.put("share_count",vedio.getShareFlag());
        }
        if(vedio.getPointFlag()!=null){
            sort.put("point_count",vedio.getPointFlag());
        }
        if(vedio.getCommentFlag()!=null){
            sort.put("comment_count",vedio.getCommentFlag());
        }
        if(vedio.getCollectFlag()!=null){
            sort.put("collect_count",vedio.getCollectFlag());
        }
        if(vedio.getPlayFlag()!=null){
            sort.put("play_count",vedio.getPlayFlag());
        }
        if(vedio.getCreateTimeFlag() != null) { // 新增根据注册时间来进行排序
            sort.put("create_time", vedio.getCreateTimeFlag());
        }
        List<User> list = new ArrayList<>();
        try {
            list = userMapper.getVoPage(vedio,sort);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }
}