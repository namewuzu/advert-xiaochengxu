package com.dk.controller.advert;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Advert;
import com.dk.entity.base.ResultBody;
import com.dk.service.AdvertServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
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

    @RequestMapping(value = "/upadteById")
    @ResponseBody
    public ResultBody padteById(Advert advert){
        ResultBody result = new ResultBody("更新广告成功",200);
        try{
            advert.setUpdateTime(new Date());
            advertServiceI.updateByPrimaryKeySelective(advert);
        } catch (Exception e) {
            result.setMsgAndStatus("更新广告失败",500);
        }
        return result;
    }


    /**
     * 添加广告
     * @param advert
     * @return
     */
    @RequestMapping(value = "/addAdvert")
    @ResponseBody
    public ResultBody addAdvert(Advert advert){
        ResultBody result = new ResultBody("添加广告成功",200);
        try{
            advert.setCreateTime(new Date());
            advertServiceI.insertSelective(advert);
        } catch (Exception e) {
            result.setMsgAndStatus("添加广告失败",500);
        }
        return result;
    }


}
