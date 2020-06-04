package com.dk.controller.advert;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Advert;
import com.dk.entity.ResultBody;
import com.dk.service.AdvertServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wuzu on 2019/3/13.
 */
@Controller
@RequestMapping("/advert")
public class AdvertController {
    private Logger log = LoggerFactory.getLogger(AdvertController.class);
    @Autowired
    private AdvertServiceI advertServiceI;

    /**
     * 广告
     * @return
     */
    @RequestMapping(value = "/selectByParm")
    @ResponseBody
    public ResultBody<List<Advert>> selectAll(){
        ResultBody<List<Advert>> result = new ResultBody("查询广告信息成功",200);
        try{
            List<Advert> list =  advertServiceI.selectAll();
            result.setObj(list);
        } catch (Exception e) {
            result.setMsgAndStatus("查询广告信息失败",500);
        }
        return result;
    }


    /**
     * 获取小程序广告
     * @param advert
     * @return
     */
    @RequestMapping(value = "/selectByAdvertType")
    @ResponseBody
    public ResultBody<List<Advert>> selectByAdvertType(Advert advert){
        ResultBody<List<Advert>> result = new ResultBody("查询广告信息成功",200);
        if(advert.getAdvertType()==null){
            result.setMsgAndStatus("广告类型不能为空",500);
            return  result;
        }
        try{
            List<Advert> list =  advertServiceI.selectByAdvertType(advert.getAdvertType());
            result.setObj(list);
        } catch (Exception e) {
            log.info("查询广告信息失败",e);
            result.setMsgAndStatus("查询广告信息失败",500);
        }
        return result;
    }


}
