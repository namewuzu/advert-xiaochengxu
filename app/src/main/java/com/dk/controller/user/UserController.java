package com.dk.controller.user;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.dk.entity.User;
import com.dk.entity.ResultBody;
import com.dk.service.UserServiceI;
import com.dk.service.VedioServiceI;
import com.dk.util.Constants;
import com.dk.util.HttpRequestUtil;
import com.dk.util.PageResult;
import com.dk.util.StringUtils;
import com.dk.vo.VedioTypeVo;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wuzu on 2019/3/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private VedioServiceI vedioServiceI;

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping("/getUserInfor")
    @ResponseBody
    public ResultBody  getAuthorVoById(Integer userId){
        ResultBody result = new ResultBody<>("获取单个用户信息成功",200);
        try {
            User userInfor = new User();
            userInfor = userServiceI.selectByPrimaryKey(userId);
            if(userInfor==null){
                result.setObj(new JsonObject());
            }
            result.setObj(userInfor);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("获取单个用户信息失败",500);
            log.info("获取单个用户失败",e);
            return result;
        }
    }

    /**
     * 授权保存用户信息
     * @param user
     * @return
     */
    @RequestMapping("/saveUserInfor")
    @ResponseBody
    public ResultBody saveUserInfor(User user){
        ResultBody result = new ResultBody<>("微信授权保存用户信息成功",200);
        if(user.getId()==null){
            result.setMsgAndStatus("id不能为空",500);
            return  result;
        }
        if(user.getNickName()==null){
            result.setMsgAndStatus("昵称不能为空",500);
            return  result;
        }
        if(user.getAvatarUrl()==null){
            result.setMsgAndStatus("用户头像地址不能为空",500);
            return  result;
        }

        try {
            User userInfor = userServiceI.saveUserInfor(user);
            if(userInfor==null){
                result.setObj(new JsonObject());
            }
            result.setObj(userInfor);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("微信授权保存用户信息失败",500);
            log.info("微信授权保存用户信息失败",e);
            return result;
        }
    }

    /**
     * 用户第一次进入小程序登录操作，没有就插入到数据库
     * @param code
     * @return
     */
    @RequestMapping("/loginUser")
    @ResponseBody
    public ResultBody loginUser(String code){
        ResultBody result = new ResultBody<>("用户登录成功",200);
        if(code==null || code==""){
            result.setMsgAndStatus("code不能为空",500);
            return  result;
        }
        try {
            User user = userServiceI.loginUser(code);
            if(user==null){
                result.setObj(new JsonObject());
            }
            result.setObj(user);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("用户登录失败",500);
            log.info("用户登录失败",e);
            return result;
        }
    }

    /**
     * type 1-用户分享的，2-用户点赞的，3-用户评论的，4-用户播放的，5-用户收藏的
     * @param page
     * @param vedioTypeVo
     * @return
     */
    @RequestMapping("/getUserVedio")
    @ResponseBody
    public ResultBody<PageResult<VedioTypeVo>> getUserVedio(PageResult<VedioTypeVo> page, VedioTypeVo vedioTypeVo){
        ResultBody<PageResult<VedioTypeVo>> result = new ResultBody("获取用户视频成功",200);
        if(vedioTypeVo.getType()==null){
            result.setMsgAndStatus("type不能为空",500);
            return result;
        }
        try {
            vedioServiceI.queryTypeByPage(page,vedioTypeVo);
            result.setObj(page);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("获取用户视频失败",500);
            log.info("获取用户视频失败",e);
            return result;
        }
    }

    /**
     * 根据code获取用户信息
     * @param code
     * @return
     */
    @RequestMapping("/autoInfor")
    @ResponseBody
    public ResultBody<User> autoInfor(String code){
        ResultBody<User> result = new ResultBody("获取用户信息成功",200);
        if(StringUtils.isBlank(code)){
            result.setMsgAndStatus("code不能为空",500);
            return result;
        }
        try {
            //根据code 获取openid
            String openId=null;
            String token=null;
            JSONObject openIdJson = HttpRequestUtil.httpGet(Constants.GET_OPENID_URL.replace("APPID",Constants.WX_APP_ID).replace
                    ("SECRET",Constants.WX_APP_SECRET).replace("CODE",code));
            if(openIdJson!=null){
                openId = openIdJson.getString("openid");
            }
            //获取accessToken
            JSONObject tokenJson = HttpRequestUtil.httpGet(Constants.GET_TOKEN_URL.replace("APPID",Constants.WX_APP_ID).replace
                    ("APPSECRET",Constants.WX_APP_SECRET));
            if(tokenJson!=null){
                token = tokenJson.getString("access_token");
            }
            //获取用户信息
            if(openId!=null && token!=null){
                JSONObject userInforJson = HttpRequestUtil.httpGet(Constants.GET_USER_INFO_URL.replace("ACCESS_TOKEN",token).replace
                        ("OPENID",openId));
                User user = userServiceI.parseUser(userInforJson,openId);
                if(user!=null){
                    result.setObj(user);
                }
            }else{
                result.setMsgAndStatus("获取用户信息失败",500);
            }
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("获取用户信息失败",500);
            log.info("获取用户信息失败",e);
            return result;
        }
    }
}
