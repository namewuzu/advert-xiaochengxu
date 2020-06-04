package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Order;
import com.dk.entity.Vedio;
import com.dk.mapper.OrderMapper;
import com.dk.mapper.VedioMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.OrderServiceI;
import com.dk.service.VedioServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.PageResult;
import com.dk.vo.OrderVo;
import com.dk.vo.VedioVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderServiceI {
    private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderMapper orderMapper;

    @Override
    protected IBaseDao<Order> getMapper() {
        return orderMapper;
    }

    @Override
    public PageResult<OrderVo> queryByVoPage(PageResult<OrderVo> page, OrderVo orderVo) {
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getVoPage(orderVo),page);
    }

    public List<OrderVo> getVoPage(OrderVo vedio) {
        List<OrderVo> list = new ArrayList<>();
        try {
            list = orderMapper.getVoPage(vedio);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }
}