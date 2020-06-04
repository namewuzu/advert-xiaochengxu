package com.dk.controller.order;

import com.dk.entity.ResultBody;
import com.dk.service.WeixinPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wuzu on 2019/5/8.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    private Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private WeixinPayService weixinPayService;

    @RequestMapping("/reOrder")
    @ResponseBody
    public ResultBody reOrder(HttpServletRequest request, HttpServletResponse response, String code,
                              String ip, Integer fee,Integer userId) {
        try {
            return weixinPayService.reOrder(request, response, code, ip, fee,userId);
        } catch (Exception e) {
            log.error("统计订单返回数据失败",e);
            return new ResultBody("获取数据异常");
        }
    }

    /**
     * 支付成功通知
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping("/notify")
    public void notity(HttpServletResponse response,HttpServletRequest request) {
        try {
            weixinPayService.notity(response, request);
        } catch (Exception e) {
            log.error("通知回调 微信服务器 异常", e);
        }
    }


    /**
     * 处理失败订单
     * @param orderNo
     * @return
     */
    @RequestMapping("/failOrder")
    public ResultBody failOrder(String orderNo) {
        ResultBody result = new ResultBody<>("处理失败订单成功",200);
        try {
            weixinPayService.failOrder(orderNo);
        } catch (Exception e) {
            result.setMsgAndStatus("处理失败订单异常",500);
            log.error("处理失败订单异常", e);
        }
        return result;
    }
}
