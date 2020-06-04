package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Comment;
import com.dk.mapper.CommentMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.CommentServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.PageResult;
import com.dk.vo.CommentVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentServiceI {

    private Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentMapper commentMapper;

    @Override
    protected IBaseDao<Comment> getMapper() {
        return commentMapper;
    }

    @Override
    public PageResult<CommentVo> queryVoByPage(PageResult<CommentVo> page, CommentVo commentVo) {
        int pageNo = page.getPageNo(); // 要显示的页数
        int pageSize = page.getPageSize(); // 每页显示多少条
        pageNo = pageNo == 0 ? 1 : pageNo;
        pageSize = pageSize == 0 ? 10 : pageSize;
        PageHelper.startPage(pageNo, pageSize);
        return PageResult.toPageResult(getVoPage(commentVo), page);
    }

    public List<CommentVo> getVoPage(CommentVo commentVo) {
        List<CommentVo> list = new ArrayList<>();
        try {
            list = commentMapper.getVoPage(commentVo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }
}