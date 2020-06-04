package com.dk.controller.vedio;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.ResultBody;
import com.dk.entity.Vedio;
import com.dk.service.CategoryServiceI;
import com.dk.service.CommentServiceI;
import com.dk.service.VedioServiceI;
import com.dk.util.PageResult;
import com.dk.util.StringUtils;
import com.dk.vo.CategoryVo;
import com.dk.vo.CommentVo;
import com.dk.vo.VedioInforVo;
import com.dk.vo.VedioVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzu on 2019/4/22.
 */
@Controller
@RequestMapping("/vedio")
public class VedioController {
    private Logger log = LoggerFactory.getLogger(VedioController.class);
    @Autowired
    private VedioServiceI vedioServiceI;
    @Autowired
    private CategoryServiceI categoryServiceI;
    @Autowired
    private CommentServiceI commentServiceI;

    /**
     * 获取所有视频分类信息
     * @return
     */
    @RequestMapping("/getCategorys")
    @ResponseBody
    public ResultBody<List<CategoryVo>> getCategorys(){
        ResultBody<List<CategoryVo>> result = new ResultBody<>("获取视频分类信息成功",200);
        try {
            List<CategoryVo> list = new ArrayList<>();
            list = categoryServiceI.selectAll();
            result.setObj(list);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("获取视频分类信息失败",500);
            log.info("获取视频分类信息息失败",e);
            return result;
        }
    }


    /**
     * 根据分类id获取视频分页数据
     * @param page
     * @param vo
     * @return
     */
    @RequestMapping("/getVedioByCategoryId")
    @ResponseBody
    public ResultBody<PageResult<VedioVo>> getVedioByCategoryId(PageResult page,VedioVo vo){
        ResultBody<PageResult<VedioVo>> result = new ResultBody<>("根据分类id获取视频信息成功",200);
        if(StringUtils.isBlank(vo.getCategoryId())){
            result.setMsgAndStatus("分类id不能为空",500);
            return  result;
        }
        if(vo.getUserId()==null){
            result.setMsgAndStatus("用户id不能为空",500);
            return  result;
        }
        try {
            log.info("***********************进入方法中"+vo.getCategoryId()+"*****"+vo.getUserId());
            vedioServiceI.getVoPage(page,vo);
            result.setObj(page);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("根据分类id获取视频信息失败",500);
            log.info("根据分类id获取视频信息失败",e);
            return result;
        }
    }


    /**
     * 根据视频分类id随机获取8条视频数据
     * @return
     */
    @RequestMapping("/getRandomVedios")
    @ResponseBody
    public ResultBody<PageResult<Vedio>> getRandomVedios(PageResult page, Vedio vo){
        ResultBody<PageResult<Vedio>> result = new ResultBody<>("随机获取视频信息成功",200);
        try {
            List<Vedio> list = new ArrayList<>();
            list= vedioServiceI.getRandomVedios();
            page = PageResult.toPageResult(list,page);
            result.setObj(page);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("随机获取视频信息失败",500);
            log.info("随机获取视频信息失败",e);
            return result;
        }
    }

    /**
     * 根据视频分类id随机获取8条视频数据 (分页)
     * @return
     */
    /*@RequestMapping("/getRandomVedios")
    @ResponseBody
    public ResultBody<PageResult<VedioVo>> getRandomVedios(PageResult page,VedioVo vo){
        ResultBody<PageResult<VedioVo>> result = new ResultBody<>("随机获取视频信息成功",200);
        try {
            vedioServiceI.getRandomVoPage(page,vo);
            result.setObj(page);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("随机获取视频信息失败",500);
            log.info("随机获取视频信息失败",e);
            return result;
        }
    }*/

    /**
     * 根据视频id获取视频信息
     * @param vedioId
     * @return
     */
    @RequestMapping("/getVedioById")
    @ResponseBody
    public ResultBody<VedioInforVo> getVedioById(Integer vedioId,Integer userId){
        ResultBody<VedioInforVo> result = new ResultBody<>("根据视频id获取视频信息成功",200);
        if(vedioId==null){
            result.setMsgAndStatus("视频id不能为空",500);
            return  result;
        }
        if(userId==null){
            result.setMsgAndStatus("用户id不能为空",500);
            return  result;
        }
        try {
            VedioInforVo vo = new VedioInforVo();
            vo= vedioServiceI.getVedioById(vedioId,userId);
            //获取视频开始的两条评论
            List<CommentVo> commentVos = commentServiceI.getTwoRecord(vedioId);
            if(commentVos!=null && commentVos.size()>0){
                vo.setComments(commentVos);
            }
            result.setObj(vo);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("根据视频id获取视频信息失败",500);
            log.info("根据视频id获取视频信息失败",e);
            return result;
        }
    }
}
