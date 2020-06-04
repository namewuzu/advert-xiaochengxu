package com.dk.controller.user;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.User;
import com.dk.entity.base.ResultBody;
import com.dk.service.UserServiceI;
import com.dk.util.DateUtils;
import com.dk.util.PageResult;
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
@RequestMapping("/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserServiceI userServiceI;


    /**
     * 用户列表
     * @param page
     * @param user
     * @return
     */
    @RequestMapping(value = "/selectByParm")
    @ResponseBody
    public ResultBody<PageResult<User>> selectByParm(PageResult<User> page, UserVo user){
        ResultBody<PageResult<User>> result = new ResultBody("根据条件查询用户信息",200);
        try{
            if(user.getEndDate()!=null){
                Date endDate = DateUtils.addDay(user.getEndDate(),1);
                user.setEndDate(endDate);
            }
            userServiceI.queryVoByPage(page,user);
            result.setObj(page);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询用户信息失败",500);
        }
        return result;
    }

}
