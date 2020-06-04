package com.dk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dk.entity.Order;
import com.dk.entity.ResultBody;
import com.dk.entity.User;
import com.dk.mapper.OrderMapper;
import com.dk.mapper.UserMapper;
import com.dk.service.WeixinPayService;
import com.dk.util.Constants;
import com.dk.util.HttpRequestUtil;
import com.dk.util.IPUtil;
import com.dk.util.weixin.pay.WXPay;
import com.dk.util.weixin.pay.WXPlayConfigImpl;
import com.dk.util.weixin.pay.XMLParser;
import com.dk.util.weixin.sdk.WXPayConstants;
import com.dk.util.weixin.sdk.WXPayUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzu on 2019/5/8.
 */
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    private Logger log = LoggerFactory.getLogger(WeixinPayService.class);
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResultBody reOrder(HttpServletRequest request, HttpServletResponse response, String code, String ip,
                              Integer fee,Integer userId) throws Exception {
        ResultBody result = new ResultBody("统一订单信息成功",200);
        //获取openid
       /* JSONObject openIdJson = HttpRequestUtil.httpGet(Constants.GET_OPENID_URL.replace("APPID",Constants.WX_APP_ID).replace
                ("SECRET",Constants.WX_APP_SECRET).replace("CODE",code));*/
        String openId = null;
        User user = userMapper.selectByPrimaryKey(userId);
        if(fee==null || fee<=0){
            result.setMsgAndStatus("获取统一订单失败,金额不能小于0",500);
            return result;
        }
        fee=fee*100;//费用转换为分
        if(user==null) {
            result.setMsgAndStatus("获取统一订单失败,获取openid失败,用户为空",500);
            return result;
        }
        openId = user.getOpenId();
        //统一下单接口
        WXPlayConfigImpl wxPayConfig = new WXPlayConfigImpl();
        WXPay pay = new WXPay(wxPayConfig,Constants.WX_PAY_NOTIFY,true,true);
        log.info(wxPayConfig.getWXPayDomain().getDomain(wxPayConfig).domain);
        Map<String,String> data = new HashMap<>();
        //data = pay.fillRequestData(data);
        //设置支付相关数据
        String orderNo = getOrderNo(userId);
        data.put("body","用户打赏");
        data.put("detail","用户打赏支付");
        data.put("openid",openId);
        data.put("out_trade_no",orderNo);
        data.put("fee_type","CNY");
        data.put("device_info","WEB");
        data.put("total_fee",fee+"");//支付金额
        data.put("spbill_create_ip", IPUtil.getIp(request));//终端ip
        data.put("trade_type", "JSAPI");//交易类型
        log.info("openId="+openId+"...ip="+ip+"...fee="+fee+"...orderNo="+orderNo);
        //统一下单返回数据
        Map<String,String> resultMap = pay.unifiedOrder(data);
        Map<String, String> rsMap = new HashedMap();//微信支付需要的参数
        rsMap.put("appId", Constants.WX_APP_ID);
        rsMap.put("nonceStr",WXPayUtil.generateNonceStr());//生成随机字符串
        rsMap.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));//时间戳
        rsMap.put("signType", "MD5");//时间戳
        if (resultMap != null && resultMap.get("return_code").equals("SUCCESS")) {
            if (resultMap.get("result_code").equals("SUCCESS")) {
                rsMap.put("package", "prepay_id=" + (String) resultMap.get("prepay_id"));
                //签名
                String sign = WXPayUtil.generateSignature(rsMap, Constants.WX_MUCH_SECRET, WXPayConstants.SignType.MD5);
                rsMap.put("paySign", sign);
                rsMap.put("orderNo", orderNo);//支付订单号
                log.info("第二次签名................"+sign+"...appId="+Constants.WX_APP_ID+".......nonceStr");
                //插入订单，订单状态支付中
                Order order = new Order();
                order.setCreateTime(new Date());
                order.setFee(fee);
                order.setOrderId(orderNo);
                order.setReOrderId(resultMap.get("prepay_id"));
                order.setUserId(userId);
                order.setOrderStatus((byte) 2);
                order.setRemark("打赏充值");
                orderMapper.insertSelective(order);
                result.setObj(rsMap);
            }else{
                result.setMsgAndStatus("获取预订单失败",500);
                return  result;
            }
        }else{
            result.setMsgAndStatus("获取预订单失败",500);
            return  result;
        }
        return result;
    }

    /**
     * 微信支付通知
     * @param response
     * @param request
     */
    @Override
    public void notity(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, String> map = XMLParser.parseXmlFromRequest(request);
        boolean isSign = WXPayUtil.isSignatureValid(map,Constants.WX_MUCH_SECRET);
        //判断签名是否正确
        if(isSign) {
            //当前订单的通知业务
            if ("SUCCESS".equals(map.get("return_code"))) {
                if ("SUCCESS".equals(map.get("result_code"))) {
                    log.info("................................通知返回开始");
                    String out = String.valueOf(map.get("out_trade_no"));
                    int total_fee = Integer.parseInt(String.valueOf(map.get("total_fee")));//订单金额
                    String transactionNo = String.valueOf(map.get("transaction_id"));//微信支付订单号
                    log.info("...........返回商户订单号=" + out + "....返回微信订单号码=" + transactionNo);
                    //支付交易成功
                    Order order = orderMapper.getOrderById(out);
                    order.setOrderStatus((byte) 3);
                    order.setUpdateTime(new Date());
                    order.setRemark("用户打赏,订单成功");
                    orderMapper.updateByPrimaryKeySelective(order);
                    String result = "<xml>" +
                            "<return_code><![CDATA[SUCCESS]]></return_code>" +
                            "<return_msg><![CDATA[OK]]></return_msg>" +
                            "</xml> ";
                    log.info("................................通知返回xml=" + result);
                    try {
                        response.getWriter().write(result);
                    } catch (IOException e) {
                        log.error("通知回调 微信服务器 异常", e);
                    }
                }
                //通知成功
                log.info("................................通知返回结束");
            }
        }
    }

    @Override
    public void failOrder(String orderNo) {
        Order order = new Order();
        order.setOrderStatus((byte) 4);
        order.setOrderId(orderNo);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 生成打赏订单号 DS 开头
     * @param userId
     * @return
     */
    public  synchronized  String getOrderNo(Integer userId){
        return  "DS_"+userId+"_"+System.currentTimeMillis();
    }
}
