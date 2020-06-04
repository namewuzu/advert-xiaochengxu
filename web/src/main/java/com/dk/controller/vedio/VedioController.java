package com.dk.controller.vedio;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Category;
import com.dk.entity.Vedio;
import com.dk.entity.base.ResultBody;
import com.dk.service.CategoryServiceI;
import com.dk.service.VedioServiceI;
import com.dk.service.impl.YidianzixunService;
import com.dk.util.PageResult;
import com.dk.vo.VedioIdVo;
import com.dk.vo.VedioVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wuzu on 2019/3/13.
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
    private YidianzixunService yidianzixunService;

    @RequestMapping(value = "/selectByParm")
    @ResponseBody
    public ResultBody<PageResult<VedioVo>> selectByParm(PageResult<VedioVo> page, VedioVo vedio){
        ResultBody<PageResult<VedioVo>> result = new ResultBody("根据条件查询视频信息",200);
        try{
            vedioServiceI.queryByVoPage(page,vedio);
            result.setObj(page);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询视频信息失败",500);
        }
        return result;
    }

    @RequestMapping(value = "/selectAllCategory")
    @ResponseBody
    public ResultBody<PageResult<Category>> selectAllCategory(PageResult<Category> page, Category vo){
        ResultBody<PageResult<Category>> result = new ResultBody("根据条件查询视频分类信息",200);
        try{
            categoryServiceI.queryByPage(page,vo);
            result.setObj(page);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询视频分类信息失败",500);
        }
        return result;
    }

    /**
     * 根据视频ID来更新视频权重
     * @param vedioVo 需要传入视频ID(id)和新的视频权重(weight)
     * @return
     */
    @RequestMapping(value = "/updateVedioWeight")
    @ResponseBody
    public ResultBody<Object> updateVedioWeightById(VedioVo vedioVo) {
        ResultBody<Object> result = new ResultBody<>("修改视频权重成功", 200);
        try {
            vedioServiceI.updateByPrimaryKeySelective(vedioVo);
        } catch (Exception e) {
            result.setMsgAndStatus("修改视频权重失败", 500);
        }
        return result;
    }

    /**
     * 爬取视频
     * @param vedioVo
     * @return
     */
    @RequestMapping(value = "/spliteVedios")
    @ResponseBody
    public ResultBody<List<Vedio>> spliteVedios(VedioVo vedioVo) {
        ResultBody<List<Vedio>> result = new ResultBody<>("爬取视频成功", 200);
        try {
            List<Vedio> list = yidianzixunService.setInter();
            if(list!=null && list.size()>0){
                result.setObj(list);
            }
        } catch (Exception e) {
            result.setMsgAndStatus("爬取视频失败", 500);
            log.info("爬取视频失败",e);
        }
        return result;
    }

    @RequestMapping(value = "/spliteVediosSelect")
    @ResponseBody
    public ResultBody<List<Vedio>> spliteVediosSelect() {
        ResultBody<List<Vedio>> result = new ResultBody<>("获取爬取视频成功", 200);
        try {
            List<Vedio> list =  yidianzixunService.getSpliterVedios();
            if(list!=null && list.size()>0){
                result.setObj(list);
            }
        } catch (Exception e) {
            result.setMsgAndStatus("获取爬取视频失败", 500);
            log.info("获取爬取视频失败",e);
        }
        return result;
    }


    @RequestMapping(value = "/spliteVediosAdd")
    @ResponseBody
    public ResultBody spliteVediosAdd(@RequestBody List<Vedio> vedios) {
        ResultBody result = new ResultBody<>("新增爬取视频成功", 200);
        try {
            yidianzixunService.spliteVediosAdd(vedios);
        } catch (Exception e) {
            result.setMsgAndStatus("新增爬取视频失败", 500);
            log.info("新增爬取视频失败",e);
        }
        return result;
    }

    /**
     * 查询需要删除的视频
     * @param pageResult
     * @return
     */
    @RequestMapping("/findNeedDeleteVedios")
    @ResponseBody
    public ResultBody<PageResult<Vedio>> findNeedDeleteVedios(PageResult<Vedio> pageResult) {
        ResultBody<PageResult<Vedio>> result = new ResultBody("查询需要删除的视频成功",200);
        try{
            vedioServiceI.findNeedDeleteVedios(pageResult);
            result.setObj(pageResult);
        } catch (Exception e) {
            result.setMsgAndStatus("查询需要删除的视频失败",500);
        }
        return result;
    }

    /**
     * 删除指定的视频，前端传入：[52235, 26635,……]
     * @param vedioIdVo
     * @return
     */
    @PostMapping("/deleteVediosByIds")
    @ResponseBody
    public ResultBody<Object> deleteVediosByIds(@RequestBody VedioIdVo vedioIdVo) {
        ResultBody<Object> result = new ResultBody("删除指定视频成功",200);
        try {
            if (vedioIdVo != null && vedioIdVo.getVedioIds() != null && vedioIdVo.getVedioIds().size() > 0) {
                int effectRows = vedioServiceI.deleteVediosByIds(vedioIdVo.getVedioIds());
                result.setMsg("本次共删除" + effectRows +"条视频");
            } else {
                result.setMsg("请选择要删除的视频");
            }
        } catch (Exception e) {
            result.setMsgAndStatus("删除指定视频失败",500);
            log.info("删除指定视频失败", e);
        }
        return result;
    }

    /**
     * 删除(逻辑删除)那些分享/播放、点赞/播放、评论/播放、收藏/播放同时为0的视频
     * @return
     */
    @GetMapping("/cleanVedios")
    @ResponseBody
    public ResultBody<Object> cleanVedios() {
        ResultBody<Object> result = new ResultBody<>("清除视频成功", 200);
        try {
            int effectRows = vedioServiceI.cleanVedios();
            result.setMsg("本次共清除视频" + effectRows + "条");
        } catch (Exception e) {
            result.setMsgAndStatus("清除视频失败", 500);
            log.info("清除失败失败", e);
        }
        return result;
    }

}
