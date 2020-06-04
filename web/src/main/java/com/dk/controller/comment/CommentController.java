package com.dk.controller.comment;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.User;
import com.dk.entity.base.ResultBody;
import com.dk.service.CommentServiceI;
import com.dk.service.UserServiceI;
import com.dk.util.DateUtils;
import com.dk.util.PageResult;
import com.dk.vo.CommentVo;
import com.dk.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by wuzu on 2019/3/13.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    private Logger log = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private CommentServiceI commentServiceI;


    /**
     * 用户列表
     * @param page
     * @param vo
     * @return
     */
    @RequestMapping(value = "/selectByParm")
    @ResponseBody
    public ResultBody<PageResult<CommentVo>> selectByParm(PageResult<CommentVo> page, CommentVo vo){
        ResultBody<PageResult<CommentVo>> result = new ResultBody("根据条件查询评论信息",200);
        try{
            if(vo.getEndDate()!=null){
                Date endDate = DateUtils.addDay(vo.getEndDate(),1);
                vo.setEndDate(endDate);
            }
            commentServiceI.queryVoByPage(page,vo);
            result.setObj(page);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询评论信息失败",500);
        }
        return result;
    }

}
