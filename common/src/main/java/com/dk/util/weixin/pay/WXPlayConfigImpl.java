package com.dk.util.weixin.pay;

import com.dk.util.Constants;

import java.io.InputStream;

/**
 * Created by wuzu on 2019/5/8.
 */
public class WXPlayConfigImpl extends WXPayConfig{
    private  String appId=Constants.WX_APP_ID;
    private  String machId=Constants.WX_MUCH_ID;
    private  String key=Constants.WX_MUCH_SECRET;
    private  InputStream certStream;
    private  IWXPayDomain wXPayDomain;

    public WXPlayConfigImpl() {
        this.appId = Constants.WX_APP_ID;
        this.certStream = null;
        this.key = Constants.WX_APP_SECRET;
        this.machId = Constants.WX_MUCH_ID;
        this.wXPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                DomainInfo domainInfo = new  DomainInfo(Constants.WX_PAY_DOMAIN,true);
                return domainInfo;
            }
        };
    }

    @Override
    public String getAppID() {
        return this.appId;
    }

    @Override
    public String getMchID() {
        return this.machId;
    }

    @Override
    public String getKey() {
        return Constants.WX_MUCH_SECRET;
    }

    @Override
    public InputStream getCertStream() {
        return this.certStream;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return this.wXPayDomain;
    }
}
