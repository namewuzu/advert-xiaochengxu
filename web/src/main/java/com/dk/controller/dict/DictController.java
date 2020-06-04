package com.dk.controller.dict;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Dict;
import com.dk.entity.base.ResultBody;
import com.dk.service.DictServiceI;
import com.dk.util.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.RedisConstanct;

import java.util.List;

/**
 * Created by wuzu on 2019/3/13.
 */
@Controller
@RequestMapping("/dict")
public class DictController {
    private Logger log = LoggerFactory.getLogger(DictController.class);
    @Autowired
    private DictServiceI dictServiceI;

    /**
     * 订单列表
     * @return
     */
    @RequestMapping(value = "/selectAll")
    @ResponseBody
    public ResultBody<List<Dict>> selectAll(){
        ResultBody<List<Dict>> result = new ResultBody("根据条件查询全局参数信息",200);
        try{
            List<Dict> list =  dictServiceI.selectAll();
            result.setObj(list);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询全局参数失败",500);
        }
        return result;
    }

    @RequestMapping(value = "/upadteById")
    @ResponseBody
    public ResultBody<List<Dict>> upadteById(Dict dict){
        ResultBody<List<Dict>> result = new ResultBody("更新数据成功",200);
        try{
            dictServiceI.updateDbAndRedis(dict);
        } catch (Exception e) {
            result.setMsgAndStatus("更新数据失败",500);
        }
        return result;
    }


}
