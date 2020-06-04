package com.dk.controller.comment;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Comment;
import com.dk.entity.ResultBody;
import com.dk.mapper.VedioMapper;
import com.dk.service.CommentServiceI;
import com.dk.service.VedioServiceI;
import com.dk.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by wuzu on 2019/4/23.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    private Logger log = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private CommentServiceI commentServiceI;
    @Autowired
    private VedioServiceI vedioServiceI;

    /**
     * 评论
     * @param comment
     * @return
     */
    @RequestMapping("/commentVedio")
    @ResponseBody
    public ResultBody commentVedio(Comment comment){
        ResultBody result = new ResultBody<>("评论视频成功",200);
        if(comment.getVedioId()==null){
            result.setMsgAndStatus("视频id不能为空",500);
            return result;
        }
        try {
            comment.setCreateTime(new Date());
            commentServiceI.commentVedio(comment);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("评论视频失败",500);
            log.info("评论视频失败",e);
            return result;
        }
    }

    /**
     * 根据视频id获取评论（分页）
     * @param comment
     * @return
     */
    @RequestMapping("/getCommentsByVedioId")
    @ResponseBody
    public ResultBody<PageResult<Comment>> getCommentsByVedioId(PageResult<Comment> page,Comment comment){
        ResultBody<PageResult<Comment>> result = new ResultBody("获取评论成功",200);
        try {
            commentServiceI.queryByPage(page,comment);
            result.setObj(page);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("获取评论失败",500);
            log.info("获取评论失败",e);
            return result;
        }
    }
}
