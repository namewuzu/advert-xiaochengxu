package com.dk.controller.user;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Admin;
import com.dk.entity.base.ResultBody;
import com.dk.service.AdminServiceI;
import com.dk.util.PageResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by wuzu on 2019/3/13.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminServiceI adminServiceI;

    @RequestMapping(value = "/login")
    @ResponseBody
    public ResultBody login(HttpServletRequest request,String username, String password){
        ResultBody result = new ResultBody("登录成功",200);
        log.info("开始登录");
        //构建登录 token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //设置记住我
        token.setRememberMe(true);
        //获取当前登录用户
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //登录
            currentUser.login(token);
            //判断用户状态是否已经被认证
            if (currentUser.isAuthenticated()){
                result.setMsgAndStatus("登录成功",200);
                //创建session对象
                HttpSession session = request.getSession();
                //把用户数据保存在session域对象中
                session.setAttribute("loginName", username);
                return  result;
            }else {
                result.setMsgAndStatus("系统异常，请重试",500);
            }
        } catch (UnknownAccountException uae) {
            result.setMsgAndStatus("未知用户",500);
        } catch (IncorrectCredentialsException ice) {
            result.setMsgAndStatus("认证失败",500);
        } catch (LockedAccountException lae) {
            result.setMsgAndStatus("账户已锁定",500);
        } catch (ExcessiveAttemptsException eae) {
            result.setMsgAndStatus("登录失败次数过多",500);
        } catch (AuthenticationException ae) {
            result.setMsgAndStatus("未知错误",500);
        }
        return result;
    }

    /**
     * 管理员列表
     * @param page
     * @param admin
     * @return
     */
    @RequestMapping(value = "/selectByParm")
    @ResponseBody
    public ResultBody<PageResult<Admin>> selectByParm(PageResult<Admin> page,Admin admin){
        ResultBody result = new ResultBody("查询所有后台管理员成功",200);
        try {
             adminServiceI.queryByPage(page,admin);
             result.setObj(page);
             return result;
        } catch (Exception e) {
            result.setMsgAndStatus("查询所有后台管理员失败",500);
        }
        return result;
    }


    /**
     * 修改管理员成功
     * @param admin
     * @return
     */
    @RequestMapping(value = "/updatAdminName")
    @ResponseBody
    public ResultBody updatAdminName(Admin admin){
        ResultBody result = new ResultBody("修改后台管理员成功",200);
        try {
             adminServiceI.updateByPrimaryKeySelective(admin);
             return result;
        } catch (Exception e) {
            result.setMsgAndStatus("修改后台管理员失败",500);
            log.info("修改后台管理员失败",e);
        }
        return result;
    }


    /**
     * 修改管理员密码（超级管理员才有的权限）
     * @param admin
     * @return
     */
    @RequestMapping(value = "/updatAdminPassword")
    @ResponseBody
    public ResultBody updatAdminPassword(Admin admin){
        ResultBody result = new ResultBody("修改后台管理员密码成功",200);
        if(!admin.getAccount().equals("admin")){
            result.setMsgAndStatus("没有权限修改",500);
            return  result;
        }
        try {
             adminServiceI.updateByPrimaryKeySelective(admin);
             return result;
        } catch (Exception e) {
            result.setMsgAndStatus("修改后台管理员密码失败",500);
            log.info("修改后台管理员密码失败",e);
        }
        return result;
    }
}
