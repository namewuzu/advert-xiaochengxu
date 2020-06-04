package com.dk.listener;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.util.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestSubscribe {
    private Logger log = LoggerFactory.getLogger(TestSubscribe.class);
    @Autowired
    private RedisMsgPubSubListener listener;
    public void testSubscribe(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                douIt();
            }
        }).start();
    }

    public void douIt(){
        try {
            RedisPoolUtil.psubscribe(listener);
        } catch (Exception e) {
            log.info("推送异常退出*************************************************"+e);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            douIt();
        }
    }
}  