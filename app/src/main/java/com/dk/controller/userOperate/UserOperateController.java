package com.dk.controller.userOperate;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.ResultBody;
import com.dk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wuzu on 2019/4/23.
 */
@Controller
@RequestMapping("/userOperate")
public class UserOperateController {
    private Logger log = LoggerFactory.getLogger(UserOperateController.class);
    @Autowired
    private VedioPointServiceI vedioPointServiceI;
    @Autowired
    private VedioPlayServiceI vedioPlayServiceI;
    @Autowired
    private VedioShareServiceI vedioShareServiceI;
    @Autowired
    private CollectServiceI collectServiceI;
    @Autowired
    private UserServiceI userServiceI;

    /**
     * flag (1-点赞，2-取消点赞)
     * 用户点赞
     * @return
     */
    @RequestMapping("/userPointVedio")
    @ResponseBody
    public ResultBody getAuthorVoById(Integer userId,Integer vedioId,Integer flag){
        ResultBody result = new ResultBody<>("用户点赞视频成功",200);
        try {
            vedioPointServiceI.userPointVedio(userId,vedioId,flag);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("用户点赞视频失败",500);
            log.info("用户点赞视频失败",e);
            return result;
        }
    }

    /**
     * 用户播放视频
     * @param userId
     * @param vedioId
     * @return
     */
    @RequestMapping("/userPlayVedio")
    @ResponseBody
    public ResultBody userPlayVedio(Integer userId,Integer vedioId){
        ResultBody result = new ResultBody<>("用户播放视频成功",200);
        try {
            vedioPlayServiceI.userPlayVedio(userId,vedioId);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("用户播放视频失败",500);
            log.info("用户播放视频失败",e);
            return result;
        }
    }

    /**
     * 用户分享视频
     * @param userId
     * @param vedioId
     * @return
     */
    @RequestMapping("/userShareVedio")
    @ResponseBody
    public ResultBody userShareVedio(Integer userId,Integer vedioId,Byte  shareType){
        ResultBody result = new ResultBody<>("用户分享视频成功",200);
        try {
            vedioShareServiceI.userShareVedio(userId,vedioId,shareType);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("用户分享视频失败",500);
            log.info("用户分享视频失败",e);
            return result;
        }
    }

    /**
     * 用户收藏视频
     * @param userId
     * @param vedioId
     * @return
     */
    @RequestMapping("/userCollect")
    @ResponseBody
    public ResultBody userCollect(Integer userId,Integer vedioId){
        ResultBody result = new ResultBody<>("用户收藏视频成功",200);
        try {
            collectServiceI.userCollect(userId,vedioId);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("用户收藏视频失败",500);
            log.info("用户收藏视频失败",e);
            return result;
        }
    }

    /**
     * 设置小程序主页面（方便审核）
     * @return
     */
    @RequestMapping("/getPageFlag")
    @ResponseBody
    public ResultBody getPageFlag(){
        ResultBody result = new ResultBody<>("获取小程序主页面成功",200);
        try {
            String flag =  userServiceI.getPageFlag();
            result.setObj(flag);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("获取小程序主页面失败",500);
            log.info("获取小程序主页面失败",e);
            return result;
        }
    }
}
