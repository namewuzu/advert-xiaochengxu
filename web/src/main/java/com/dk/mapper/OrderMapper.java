package com.dk.mapper;

import com.dk.entity.Order;
import com.dk.entity.OrderKey;
import com.dk.mapper.base.IBaseDao;
import com.dk.vo.OrderVo;
import com.dk.vo.VedioVo;

import java.util.List;

public interface OrderMapper extends IBaseDao<Order>{
    int deleteByPrimaryKey(OrderKey key);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(OrderKey key);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<OrderVo> getVoPage(OrderVo order);
}