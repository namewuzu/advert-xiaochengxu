package com.dk.util;

import com.alibaba.fastjson.JSONObject;
import com.dk.entity.Push;
import com.dk.util.WeixinAuthorize.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuzu on 2019/6/11.
 */
public class PushMessage {
    private static Logger log = LoggerFactory.getLogger(PushMessage.class);

    /**
     * 发送消息通知
     * @param Push
     * @return
     */
    public static boolean  sendMessageToUser(Push Push,String title,String pageUrl) throws Exception {
        //获取user信息
        String openId = Push.getOpenid();
        String formId = Push.getFormId();
        //从redis获取accessToken
        String token = null;
        String accessToken = RedisPoolUtil.getString("accessToken");
        if(accessToken!=null){
            token = accessToken.toString();
        }else{
            token = WeixinUtil.getTokenByBase();
        }
        log.info("推送的内容  formId="+Push.getFormId()+"  openId="+Push.getOpenid()+"  token="+token);
        //发送消息推送
        //随机详情页面视频地址需要获取
        /*int flag =0;
        if(Push.getFormType()==6){
            flag = 1;
            pageUrl=pageUrl+"&flag="+flag;
        }*/
        Integer code = WeixinUtil.sendMessage(token,openId,pageUrl,formId,getSendData(title),Constants.TEMPT_ID);
        if(code==0){
            return true;
        }
        return  false;
    }

    public  static JSONObject getSendData(String title) {
        JSONObject jsonObject = new JSONObject();

        JSONObject value1 = new JSONObject();
        value1.put("value",title);
        value1.put("color","#173177");
        jsonObject.put("keyword1",value1);

        JSONObject value2 = new JSONObject();
        value2.put("value",Constants.PUSH_CONTENT);
        value2.put("color","#173177");
        jsonObject.put("keyword2",value2);

        return  jsonObject;
    }

}
