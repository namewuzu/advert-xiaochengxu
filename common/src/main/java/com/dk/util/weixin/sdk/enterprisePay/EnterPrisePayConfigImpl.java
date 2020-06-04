package com.dk.util.weixin.sdk.enterprisePay;

import com.dk.util.weixin.sdk.IWXPayDomain;
import com.dk.util.weixin.sdk.WXPayConfig;
import com.dk.util.weixin.sdk.test.WXPayDomainSimpleImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 企业付款配置类
 */
public class EnterPrisePayConfigImpl extends WXPayConfig {

    private byte[] certData;
    private static EnterPrisePayConfigImpl INSTANCE;

    private EnterPrisePayConfigImpl(boolean userCert, String filePath) throws Exception{
        if(userCert==true){
            String certPath = filePath;
            File file = new File(certPath);
            InputStream certStream = new FileInputStream(file);
            this.certData = new byte[(int) file.length()];
            certStream.read(this.certData);
            certStream.close();
        }
    }

    public static EnterPrisePayConfigImpl getInstance(boolean useCert, String filePath) throws Exception{
        if (INSTANCE == null) {
            synchronized (EnterPrisePayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EnterPrisePayConfigImpl(useCert,filePath);
                }
            }
        }
        return INSTANCE;
    }
    public String getAppID(){
        return "wxccd88243d2cbd957";
    }

    public String getMchID() {
        return "1501928771";
    }

    public String getKey() {
        return "aa14eb1616a753d1d4b3cc60902DBdE8";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }
}
