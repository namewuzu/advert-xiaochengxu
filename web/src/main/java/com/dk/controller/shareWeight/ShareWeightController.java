package com.dk.controller.shareWeight;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.ShareWeight;
import com.dk.entity.base.ResultBody;
import com.dk.service.ShareWeightServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wuzu on 2019/3/13.
 */
@Controller
@RequestMapping("/shareWeight")
public class ShareWeightController {
    private Logger log = LoggerFactory.getLogger(ShareWeightController.class);
    @Autowired
    private ShareWeightServiceI shareWeightServiceI;

    /**
     * 订单列表
     * @return
     */
    @RequestMapping(value = "/selectAll")
    @ResponseBody
    public ResultBody<List<ShareWeight>> selectAll(){
        ResultBody<List<ShareWeight>> result = new ResultBody("根据条件查询权重信息",200);
        try{
            List<ShareWeight> list =  shareWeightServiceI.selectAll();
            result.setObj(list);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询权重失败",500);
        }
        return result;
    }
    
    @RequestMapping(value = "/upadteById")
    @ResponseBody
    public ResultBody<List<ShareWeight>> upadteById(ShareWeight dict){
        ResultBody<List<ShareWeight>> result = new ResultBody("更新权重数据成功",200);
        try{
            shareWeightServiceI.updateByPrimaryKeySelective(dict);
        } catch (Exception e) {
            result.setMsgAndStatus("更新权重数据失败",500);
        }
        return result;
    }


}
