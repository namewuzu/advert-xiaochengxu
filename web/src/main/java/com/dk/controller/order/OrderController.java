package com.dk.controller.order;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Order;
import com.dk.entity.base.ResultBody;
import com.dk.service.OrderServiceI;
import com.dk.util.DateUtils;
import com.dk.util.PageResult;
import com.dk.util.StringUtils;
import com.dk.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by wuzu on 2019/3/13.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    private Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderServiceI orderServiceI;

    /**
     * 订单列表
     * @param page
     * @param orderVo
     * @return
     */
    @RequestMapping(value = "/selectByParm")
    @ResponseBody
    public ResultBody<PageResult<OrderVo>> login(PageResult<OrderVo> page, OrderVo orderVo){
        ResultBody<PageResult<OrderVo>> result = new ResultBody("根据条件查询订单信息",200);
        try{
            if(orderVo.getEndDate()!=null){
                Date  endDate = DateUtils.addDay(orderVo.getEndDate(),1);
                orderVo.setEndDate(endDate);
            }
            orderServiceI.queryByVoPage(page,orderVo);
            result.setObj(page);
        } catch (Exception e) {
            result.setMsgAndStatus("根据条件查询订单信息失败",500);
        }
        return result;
    }

    /**
     * 修改订单（只能修改订单状态和订单备注）
     * @param order
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResultBody<Order> update(Order order){
        ResultBody result = new ResultBody("成功更新订单信息",200);
        try{
            orderServiceI.updateByPrimaryKeySelective(order);
        } catch (Exception e) {
            result.setMsgAndStatus("更新订单信息失败",500);
        }
        return result;
    }

    /**
     * 根据id查询订单信息
     * @param order
     * @return
     */
    @RequestMapping(value = "/selectById")
    @ResponseBody
    public ResultBody selectById(Order order){
        ResultBody result = new ResultBody("查询订单信息成功",200);
        if(StringUtils.isBlank(order.getOrderId())){
            result.setMsgAndStatus("订单id不能为空",500);
            return result;
        }
        try{
            Order or = orderServiceI.selectByPrimaryKeyString(order.getOrderId());
            if(or!=null){
                result.setObj(or);
            }
        } catch (Exception e) {
            result.setMsgAndStatus("查询订单信息失败",500);
        }
        return result;
    }
}
