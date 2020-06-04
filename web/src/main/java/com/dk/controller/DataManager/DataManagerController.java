package com.dk.controller.DataManager;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.base.ResultBody;
import com.dk.service.DataServiceI;
import com.dk.vo.BaseVo;
import com.dk.vo.DataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wuzu on 2019/3/13.
 */
@Controller
@RequestMapping("/data")
public class DataManagerController {
    private Logger log = LoggerFactory.getLogger(DataManagerController.class);
    @Autowired
    private DataServiceI dataServiceI;

    /**
     * 查询统计数据
     * @param vo
     * @return
     */
    @RequestMapping(value = "/getData")
    @ResponseBody
    public ResultBody<DataVo> getData(BaseVo vo){
        ResultBody<DataVo> result = new ResultBody("根据条件查询统计数据信息",200);
        try{
            DataVo dataVo = dataServiceI.getData(vo);
//            if(dataVo!=null){
//                result.setObj(dataVo);
//            }
            if (dataVo == null) {
                dataVo = new DataVo();
                dataVo.setUserCount(0);
                dataVo.setCollectCount(0);
                dataVo.setCommentCount(0);
                dataVo.setPlayCount(0);
                dataVo.setPointCount(0);
                dataVo.setShareCount(0);
            }
            result.setObj(dataVo);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询统计数据失败",500);
        }
        return result;
    }

}
