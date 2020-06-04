package com.dk.util.WeixinAuthorize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dk.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzu on 2017/6/20.
 */
public class WeixinUtil {
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

    /**
     * 获取token openid等信息
     * @return
     */
    public static  Map<String,String> getToken(String code,String appId,String appScrete) {
        try {
            String url = Configure.getTokenUrl();
            String grantType = "authorization_code";
            Map<String,String> map = new HashMap<>();
            map.put("appid",appId);
            map.put("secret",appScrete);
            map.put("code",code);
            map.put("grant_type",grantType);
            String result = HttpsRequest.get(url,map);
            if(StringUtils.isNotBlank(result)){
                Map<String,String> rmap = JsonUtil.fromJsonToMap(result);
                if(rmap.get("access_token")!=null){
                    return rmap;
                }
            }
        } catch (Exception e) {
            log.info("获取token失败",e);
        }
        return null;
    }

    /**
     * 基本获取token的接口
     * @return
     */
    public static  String getTokenByBase() {
        try {
            String url = Constants.PUSH_TOKEN_URL;
            String appId = Constants.WX_APP_ID;
            String appScrete = Constants.WX_APP_SECRET;
            String grantType = "client_credential";
            Map<String,String> map = new HashMap<>();
            map.put("appid",appId);
            map.put("secret",appScrete);
            map.put("grant_type",grantType);
            String result = HttpsRequest.get(url,map);
            if(StringUtils.isNotBlank(result)){
                Map<String,String> rmap = JsonUtil.fromJsonToMap(result);
                if(rmap.get("access_token")!=null){
                    String accessToken = rmap.get("access_token");
                    //放入redis中
                    RedisPoolUtil.putString("accessToken",accessToken,7000);
                    return accessToken;
                }
            }
        } catch (Exception e) {
            log.info("获取token失败",e);
        }
        return null;
    }

    /**
     * 获取用户openId
     * @param userCode
     * @return
     */
    public static String getOpenId(String userCode) {
        String openId = null;
        try {
            Map<String,String> openIdMap = new HashedMap();
            openIdMap.put("appid",Configure.getMobleAppID());
            openIdMap.put("secret",Configure.getMobleAapScrete());
            openIdMap.put("js_code",userCode);
            openIdMap.put("grant_type","authorization_code");
            String result = HttpsRequest.get(Configure.getGetOpenIdUrl(),openIdMap);
            if(StringUtils.isNotBlank(result)){
                Map<String,String> rmap = JsonUtil.fromJsonToMap(result);
                if(rmap.get("openid")!=null){
                    openId = rmap.get("openid");
                }
            }
        } catch (Exception e) {
            log.info("获取openId失败",e);
            return openId;
        }
        return openId;
    }

    /**
     * 获取微信用户昵称
     * @param access_token
     * @param openid
     * @return
     */
    public static Map<String,String> getUserInfor(String access_token,String openid) {
        String nickName = null;
        Map<String,String> rmap = null;
        try {
            Map<String,String> userMap = new HashedMap();
            userMap.put("access_token",access_token);
            userMap.put("openid",openid);
            userMap.put("lang","zh_CN");
            String result = HttpsRequest.get(Configure.getGetUserInfor(),userMap);
            log.info(".................获取用户微信信息返回数据:"+result);
            if(StringUtils.isNotBlank(result)){
                rmap = JsonUtil.fromJsonToMap(result);
                if(rmap.get("nickname")!=null){
                    return rmap;
                }
            }
        } catch (Exception e) {
            log.info("获取微信昵称失败",e);
            return rmap ;
        }
        return rmap;
    }

    /**
     * 检测token是否过期
     * @param token
     * @param openid
     * @return
     */
    public static boolean checkToken(String token,String openid) {
        boolean flag = false;
        try {
            Map<String,String> openIdMap = new HashedMap();
            openIdMap.put("access_token",token);
            openIdMap.put("openid",openid);
            String result = HttpsRequest.get(Configure.getCheckToken(),openIdMap);
            if(StringUtils.isNotBlank(result)){
                Map<String,String> rmap = JsonUtil.fromJsonToMap(result);
                if(rmap.get("errcode")!=null && Integer.parseInt(rmap.get("errcode"))==0){
                    flag=true;
                }
            }
        } catch (Exception e) {
            log.info("检测token是否过期异常",e);
            return false;
        }
        return flag;
    }

    /**
     *
     * @param token 小程序token
     * @param openId 接收者openid
     * @param page 要跳转的页面
     * @param formId 表单id
     * @param data 推送显示的内容
     * @return
     */
    public static  Integer sendMessage(String token,String openId,String page,String formId,Object data,String templateId) throws Exception {
        Integer errCode = null;
        String errmsg = null;
        try {
            String url = Constants.SEND_MESSAGE_URL+"?access_token="+token;
            Map<String,Object> map = new HashMap<>();
            map.put("touser",openId); //接受者用户id
            map.put("template_id",templateId); //所需下发的模板消息的id
            if(page!=null){
                map.put("page",page);
            }
            map.put("form_id",formId);
            map.put("data",data);
            //map.put("emphasis_keyword","keyword1.DATA");
            String result = HttpsRequest.postJson(url,map);
            log.info("通知...................result...."+result);
            if(StringUtils.isNotBlank(result)){
                // Map<String,String> rmap = JsonUtil.fromJsonToMap(result);
                JSONObject json = JSON.parseObject(result);
                if(json!=null){
                    errCode = Integer.parseInt(json.get("errcode").toString());
                    errmsg = json.get("errmsg").toString();
                    if(errCode!=null){
                        return errCode; //为0 标识推送成功
                    }
                }
            }
        } catch (Exception e) {
            log.info("发送推送消息失败errmsg="+errmsg,e);
        }
        return null;
    }


    public static void main(String[] args){
        //System.out.println(new Date().getTime());
      /*  String wxToken = WeixinUtil.getTokenByBase();
        String nickName = WeixinUtil.getUserInfor(wxToken,"og1T80Ds5dUo1lb20DEJZrbGOFZY");
       System.out.println(nickName);*/

    }
}
