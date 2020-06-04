package com.dk.listener;

import com.dk.entity.Push;
import com.dk.entity.Vedio;
import com.dk.mapper.PushMapper;
import com.dk.mapper.VedioMapper;
import com.dk.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class RedisMsgPubSubListener  extends JedisPubSub {
    private Logger log = LoggerFactory.getLogger(RedisMsgPubSubListener.class);
    @Autowired
    private VedioMapper vedioMapper;
    @Autowired
    private PushMapper pushMapper;

    @Override
     public void onPSubscribe(String pattern, int subscribedChannels) {
        log.info("onPSubscribe "
                + pattern + " " + subscribedChannels);
    }

    @Override
    public void onMessage(String channel, String message) {

    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        log.info("onPMessage pattern "
                                       + pattern + " " + channel + " " + message);
              //过期消息处理
        if(message.contains(Constants.PRE_PUSH)){
            String[] parm = message.split(",");
            Push push = new Push();
            push.setFormId(parm[1]);
            push.setOpenid(parm[2]);
            Integer id = Integer.parseInt(parm[3]);
            push.setId(id);
            log.info("formId="+parm[1]+"  openid="+parm[2]);
            boolean flag = false;
            String title = "";
            String pageUrl = "";
            try {
                //判断是否可以推送，一天推送三次，间隔两个小时，11-9点时间段推送
                //是否在时间段内
                if(!isTime("09:00","23:00")){
                    log.info("不在推送时间段内，不做推送**************openid="+parm[2]);
                    return;
                }
                String today = DateUtils.getCurrentDate();
                //判断时间间隔是否低于两个小时
                Push userLastPush = pushMapper.selectByFormId(parm[2],today);////
                if(userLastPush!=null){
                    if((DateUtils.getNowTime().getTime()-userLastPush.getUpdateTime().getTime())<=7200000){
                        log.info("推送最近一次低于两个小时，不做推送**************openid="+parm[2]);
                        return;
                    }
                }
                //判断今天是否已经推送了三次
                Integer count = pushMapper.selectCountByParam(parm[2],today);
                log.info("今天次数**************openid="+parm[2]+"  count="+count);
                if(count!=null && count>=3){
                    log.info("今天已经推送了三次，不做推送**************openid="+parm[2]);
                    return;
                }
                Integer pushShareCount = Integer.parseInt(RedisPoolUtil.getString(RedisConstanct.PUSH_SHARE_COUNT));
                log.info("pushShareCount................."+pushShareCount);
                Vedio vedio = vedioMapper.getOneRandowRecord(pushShareCount);
                if(vedio!=null){
                    title=vedio.getVedioTitle();
                    pageUrl=vedio.getDetailLink();
                }
                flag = PushMessage.sendMessageToUser(push,title,pageUrl);
                //推送标识和推送时间记录
                push.setUpdateTime(new Date());
                push.setFlag((byte) 2);
                pushMapper.updateByPrimaryKeySelective(push);
            } catch (Exception e) {
                log.info("推送异常....................",e);
            }
            if(flag==true)
                log.info("推送数据成功............................"+push.getFormId());
        }
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime,
                                         Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isTime(String startTime,String endTimes) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");// 设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(startTime);
            endTime = df.parse(endTimes);
        } catch (Exception e) {
            return false;
        }
        return  belongCalendar(now, beginTime, endTime);
    }

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");// 设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse("09:00");
            endTime = df.parse("23:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Boolean flag = belongCalendar(now, beginTime, endTime);
        System.out.println(flag);
    }



}  