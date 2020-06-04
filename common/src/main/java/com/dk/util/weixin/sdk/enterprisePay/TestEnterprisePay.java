package com.dk.util.weixin.sdk.enterprisePay;

import com.dk.util.Configure;
import com.dk.util.weixin.sdk.WXPay;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzu on 2018/4/16.
 */
public class TestEnterprisePay {
    public static void main(String[] args) throws Exception {
        EnterPrisePayOrder order = new EnterPrisePayOrder();
        Map<String, String> result = getResult(order);
        System.out.println(result);
    }

    /**
     * 企业支付接口
     * @param order
     * @return
     * @throws Exception
     */
    public static Map<String,String> getResult(EnterPrisePayOrder order) throws Exception {
        EnterPrisePayConfigImpl config=EnterPrisePayConfigImpl.getInstance(true, Configure.getCertPath());
        WXPay wxpay = new WXPay(config,null, true, true);
        HashMap<String, String> data = toMap(order);
        Map<String, String> result = wxpay.enterPrisedOrder(data);
        return result;
    }

    /**
     * 订单对象转map
     * @param order
     * @return
     */
    public static   HashMap<String, String> toMap(EnterPrisePayOrder order) {
        HashMap<String, String> data=new HashMap<String, String>();
        //data.put("device_info", order.getDevice_info());
        data.put("openid", order.getOpenid());//用户openid
        data.put("partner_trade_no", order.getPartner_trade_no());//商户订单号
        data.put("amount",order.getAmount()+"");//提现金额（单位为分）
        data.put("desc", order.getDesc());//描述
        data.put("check_name","NO_CHECK");//不需要实名校验
        data.put("spbill_create_ip", order.getSpbill_create_ip());//ip地址
        return data;
    }

}
