package com.dk.service.impl;

import com.dk.entity.ShareWeight;
import com.dk.entity.Vedio;
import com.dk.mapper.ShareWeightMapper;
import com.dk.mapper.VedioMapper;
import com.dk.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by wuzu on 2019/5/16.
 */
@Service
public class QuarzWeight {

    private Logger log = LoggerFactory.getLogger(QuarzWeight.class);

    @Autowired
    private VedioMapper vedioMapper;
    @Autowired
    private ShareWeightMapper shareWeightMapper;

    public void setWeight(){
        log.info("开始执行定时任务*****************************************************************");
        //查询所有权重范围
       /* List<ShareWeight> weights = shareWeightMapper.selectAll();
        if(weights==null || weights.size()<=0 ){
            return;
        }*/
        int num = 0;
        int pageSize = 20;
        //循环十个视频进行权重设置
        List<Vedio> list  = vedioMapper.selectNextTen(num,pageSize);
        while (list !=null && list.size()>0){
            for(Vedio vedio :list){
                Integer shareCount = vedio.getShareCount();
                Integer playCount = vedio.getPlayCount();
                if(playCount==null || playCount==0){
                    vedio.setWeight((short) 0);
                    vedioMapper.updateByPrimaryKeySelective(vedio);
                }else{
                    float dec = (float)shareCount/playCount;
                    //确定权重范围
                    vedio.setWeight(getWeight(dec));
                    vedioMapper.updateByPrimaryKeySelective(vedio);
                }
            }
            list.clear();
            num+=pageSize;
            list = vedioMapper.selectNextTen(num,pageSize);
        }
        log.info("执行定时任务完成*********************************************************");
    }

    public Short getWeight(float dec) {
        if(dec>=0 && dec<=0.1){
            return 1;
        }else if(dec>0.1 && dec<=0.2){
            return 2;
        }else if(dec>0.2 && dec<=0.3){
            return 3;
        }else if(dec>0.3 && dec<=0.4){
            return 4;
        }else if(dec>0.4 && dec<=0.5){
            return 5;
        }else if(dec>0.5 && dec<=0.6){
            return 6;
        }else if(dec>0.7 && dec<=0.7){
            return 7;
        } if(dec>0.7 && dec<=0.8){
            return 8;
        }else if(dec>0.8 && dec<=0.9){
            return 9;
        }else if(dec>0.9 && dec<=1) {
            return 10;
        }
        return 1;
    }


    /**
     * 定时更新数据汇总
     * 调用存储过程
     */
    public void updateTodayData(){
        log.info("开始执行定时任务更新每天的相关数据*****************************************************************");
        String todayString = DateUtils.curDate();
        vedioMapper.updateTodayData(todayString,todayString);
        log.info("执行定时任务更新每天的相关数据完成*********************************************************");
    }

    public static void main(String[] args){
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.println(df.format((float)1/100));
        System.out.print(1/100);
    }
}
