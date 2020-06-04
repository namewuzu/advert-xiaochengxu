package com.dk.service;

import com.dk.entity.Order;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.dk.vo.OrderVo;

public interface OrderServiceI extends IBaseService<Order>{
    PageResult<OrderVo> queryByVoPage(PageResult<OrderVo> page, OrderVo orderVo);
}
