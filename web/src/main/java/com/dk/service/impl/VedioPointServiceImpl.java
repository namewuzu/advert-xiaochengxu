package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.User;
import com.dk.entity.VedioPoint;
import com.dk.mapper.UserMapper;
import com.dk.mapper.VedioPointMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.UserServiceI;
import com.dk.service.VedioPointServiceI;
import com.dk.service.VedioServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.PageResult;
import com.dk.vo.UserVo;
import com.dk.vo.VedioPointVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class VedioPointServiceImpl extends BaseServiceImpl<VedioPoint> implements VedioPointServiceI {
    private Logger log = LoggerFactory.getLogger(VedioPointServiceImpl.class);
    @Autowired
    private VedioPointMapper vedioPointMapper;

    @Override
    protected IBaseDao<VedioPoint> getMapper() {
        return vedioPointMapper;
    }

    @Override
    public PageResult<VedioPointVo> queryVoByPage(PageResult<VedioPointVo> page, VedioPointVo vo) {
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getVoPage(vo),page);
    }

    public List<VedioPointVo> getVoPage(VedioPointVo vo) {
        List<VedioPointVo> list = new ArrayList<>();
        try {
            list = vedioPointMapper.getVoPage(vo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }
}