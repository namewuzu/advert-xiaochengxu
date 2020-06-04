package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Comment;
import com.dk.entity.VedioPlay;
import com.dk.mapper.CommentMapper;
import com.dk.mapper.VedioPlayMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.VedioCommentServiceI;
import com.dk.service.VedioPlayServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.PageResult;
import com.dk.vo.VedioCommentVo;
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
public class VedioCommentServiceImpl extends BaseServiceImpl<Comment> implements VedioCommentServiceI {
    private Logger log = LoggerFactory.getLogger(VedioCommentServiceImpl.class);
    @Autowired
    private CommentMapper commentMapper;

    @Override
    protected IBaseDao<Comment> getMapper() {
        return commentMapper;
    }

    @Override
    public PageResult<VedioCommentVo> queryVoByPage(PageResult<VedioCommentVo> page, VedioCommentVo vo) {
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getVoPage(vo),page);
    }

    public List<VedioCommentVo> getVoPage(VedioCommentVo vo) {
        List<VedioCommentVo> list = new ArrayList<>();
        try {
            list = commentMapper.getDetailVoPage(vo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }
}