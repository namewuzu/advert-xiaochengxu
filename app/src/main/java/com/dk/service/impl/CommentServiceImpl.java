package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Comment;
import com.dk.mapper.CommentMapper;
import com.dk.mapper.UserMapper;
import com.dk.mapper.VedioMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.CommentServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentServiceI {
    private Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    private CommentMapper mapper;
    @Autowired
    private VedioMapper vedioMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    protected IBaseDao<Comment> getMapper() {
        return mapper;
    }

    @Override
    public List<CommentVo> getTwoRecord(Integer vedioId) {
        return mapper.getTwoRecord(vedioId);
    }

    @Override
    public void commentVedio(Comment comment) {
        mapper.insertSelective(comment);
        //视频评论++
        vedioMapper.addCommentCount(comment.getVedioId());
        //用户评论加1
        userMapper.addCommentCount(comment.getUserId());
    }
}