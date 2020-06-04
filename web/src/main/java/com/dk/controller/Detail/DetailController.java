package com.dk.controller.Detail;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.base.ResultBody;
import com.dk.service.*;
import com.dk.util.PageResult;
import com.dk.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wuzu on 2019/3/13.
 */
@Controller
@RequestMapping("/detail")
public class DetailController {
    private Logger log = LoggerFactory.getLogger(DetailController.class);
    @Autowired
    private VedioPointServiceI vedioPointServiceI;
    @Autowired
    private VedioPlayServiceI vedioPlayServiceI;
    @Autowired
    private VedioShareServiceI vedioShareServiceI;
    @Autowired
    private VedioCommentServiceI vedioCommentServiceI;
    @Autowired
    private VedioCollectServiceI vedioCollectServiceI;

    /**
     * 点赞详情列表
     * @param page
     * @param orderVo
     * @return
     */
    @RequestMapping(value = "/selectPointDetailByParm")
    @ResponseBody
    public ResultBody<PageResult<VedioPointVo>> selectPointDetailByParm(PageResult<VedioPointVo> page, VedioPointVo orderVo){
        ResultBody<PageResult<VedioPointVo>> result = new ResultBody("根据条件查询点赞信息",200);
        try{
            vedioPointServiceI.queryVoByPage(page,orderVo);
            result.setObj(page);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询订单信息失败",500);
        }
        return result;
    }

    /**
     * 播放详情列表
     * @param page
     * @param vo
     * @return
     */
    @RequestMapping(value = "/selectPlayDetailByParm")
    @ResponseBody
    public ResultBody<PageResult<VedioPlayVo>> selectPlayDetailByParm(PageResult<VedioPlayVo> page, VedioPlayVo vo){
        ResultBody<PageResult<VedioPlayVo>> result = new ResultBody("根据条件查询播放信息",200);
        try{
            vedioPlayServiceI.queryVoByPage(page,vo);
            result.setObj(page);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询订单信息失败",500);
        }
        return result;
    }

    /**
     * 分享详情列表
     * @param page
     * @param vo
     * @return
     */
    @RequestMapping(value = "/selectShareDetailByParm")
    @ResponseBody
    public ResultBody<PageResult<VedioShareVo>> selectShareDetailByParm(PageResult<VedioShareVo> page, VedioShareVo vo){
        ResultBody<PageResult<VedioShareVo>> result = new ResultBody("根据条件查询分享信息",200);
        try{
            vedioShareServiceI.queryVoByPage(page,vo);
            result.setObj(page);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询分享信息失败",500);
        }
        return result;
    }

    /**
     * 评论详情列表
     * @param page
     * @param vo
     * @return
     */
    @RequestMapping(value = "/selectCommentDetailByParm")
    @ResponseBody
    public ResultBody<PageResult<VedioCommentVo>> selectCommentDetailByParm(PageResult<VedioCommentVo> page, VedioCommentVo vo){
        ResultBody<PageResult<VedioCommentVo>> result = new ResultBody("根据条件查询评论信息",200);
        try{
            vedioCommentServiceI.queryVoByPage(page,vo);
            result.setObj(page);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询评论信息失败",500);
        }
        return result;
    }


    /**
     * 收藏详情列表
     * @param page
     * @param vo
     * @return
     */
    @RequestMapping(value = "/selectCollectDetailByParm")
    @ResponseBody
    public ResultBody<PageResult<VedioCollectVo>> selectCollectDetailByParm(PageResult<VedioCollectVo> page, VedioCollectVo vo){
        ResultBody<PageResult<VedioCollectVo>> result = new ResultBody("根据条件查询收藏信息",200);
        try{
            vedioCollectServiceI.queryVoByPage(page,vo);
            result.setObj(page);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询收藏信息失败",500);
        }
        return result;
    }
}
