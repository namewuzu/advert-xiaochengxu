package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.VedioCollect;
import com.dk.entity.VedioPlay;
import com.dk.mapper.VedioCollectMapper;
import com.dk.mapper.VedioPlayMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.VedioCollectServiceI;
import com.dk.service.VedioPlayServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.PageResult;
import com.dk.vo.VedioCollectVo;
import com.dk.vo.VedioPlayVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class VedioCollectServiceImpl extends BaseServiceImpl<VedioCollect> implements VedioCollectServiceI {
    private Logger log = LoggerFactory.getLogger(VedioCollectServiceImpl.class);
    @Autowired
    private VedioCollectMapper vedioCollectMapper;

    @Override
    protected IBaseDao<VedioCollect> getMapper() {
        return vedioCollectMapper;
    }

    @Override
    public PageResult<VedioCollectVo> queryVoByPage(PageResult<VedioCollectVo> page, VedioCollectVo vo) {
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getVoPage(vo),page);
    }

    public List<VedioCollectVo> getVoPage(VedioCollectVo vo) {
        List<VedioCollectVo> list = new ArrayList<>();
        try {
            list = vedioCollectMapper.getVoPage(vo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }
}