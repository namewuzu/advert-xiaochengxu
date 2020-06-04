package com.dk.service.impl;

import com.dk.mapper.VedioMapper;
import com.dk.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuzu on 2019/5/16.
 */
@Service
public class QuarzUpdateData {

    private Logger log = LoggerFactory.getLogger(QuarzUpdateData.class);

    @Autowired
    private VedioMapper vedioMapper;

    /**
     * 定时更新数据汇总
     * 调用存储过程
     */
    public void updateTodayData(){
        log.info("开始执行定时任务更新每天的相关数据*****************************************************************");
        String todayString = DateUtils.currentDate();
        vedioMapper.updateTodayData(todayString,todayString);
        log.info("执行定时任务更新每天的相关数据完成*********************************************************");
    }

}
