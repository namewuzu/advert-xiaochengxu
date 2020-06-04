package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.VedioPlay;
import com.dk.entity.VedioShare;
import com.dk.mapper.VedioPlayMapper;
import com.dk.mapper.VedioShareMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.VedioPlayServiceI;
import com.dk.service.VedioShareServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.PageResult;
import com.dk.vo.VedioPlayVo;
import com.dk.vo.VedioShareVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class VedioShareServiceImpl extends BaseServiceImpl<VedioShare> implements VedioShareServiceI {
    private Logger log = LoggerFactory.getLogger(VedioShareServiceImpl.class);
    @Autowired
    private VedioShareMapper vedioShareMapper;

    @Override
    protected IBaseDao<VedioShare> getMapper() {
        return vedioShareMapper;
    }

    @Override
    public PageResult<VedioShareVo> queryVoByPage(PageResult<VedioShareVo> page, VedioShareVo vo) {
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getVoPage(vo),page);
    }

    public List<VedioShareVo> getVoPage(VedioShareVo vo) {
        List<VedioShareVo> list = new ArrayList<>();
        try {
            list = vedioShareMapper.getVoPage(vo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }
}