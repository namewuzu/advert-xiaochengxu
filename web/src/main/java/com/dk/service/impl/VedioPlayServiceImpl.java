package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.VedioPlay;
import com.dk.entity.VedioPoint;
import com.dk.mapper.VedioPlayMapper;
import com.dk.mapper.VedioPointMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.VedioPlayServiceI;
import com.dk.service.VedioPointServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.PageResult;
import com.dk.vo.VedioPlayVo;
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
public class VedioPlayServiceImpl extends BaseServiceImpl<VedioPlay> implements VedioPlayServiceI {
    private Logger log = LoggerFactory.getLogger(VedioPlayServiceImpl.class);
    @Autowired
    private VedioPlayMapper vedioPlayMapper;

    @Override
    protected IBaseDao<VedioPlay> getMapper() {
        return vedioPlayMapper;
    }

    @Override
    public PageResult<VedioPlayVo> queryVoByPage(PageResult<VedioPlayVo> page, VedioPlayVo vo) {
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getVoPage(vo),page);
    }

    public List<VedioPlayVo> getVoPage(VedioPlayVo vo) {
        List<VedioPlayVo> list = new ArrayList<>();
        try {
            list = vedioPlayMapper.getVoPage(vo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }
}