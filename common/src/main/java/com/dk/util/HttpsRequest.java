package com.dk.util;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpsRequest {
    private static Logger log = LoggerFactory.getLogger(HttpsRequest.class);
    public static final int CONNECTION_TIMEOUT = 5000;
    public static final int SOCKETCOOECTION_TIMEOUT = 10000;

    public static final int NORMAL = 200;
    private static CloseableHttpClient httpClient = createSSLClientDefault();

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return  HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            log.error("创建httpClient异常",e);
        }
        return HttpClients.createDefault();
    }

    public static String get(String url, Map<String, String> paramsMap){

        if (null == httpClient)
            httpClient = createSSLClientDefault();

        CloseableHttpClient client = httpClient;
        String responseText = null;
        HttpEntity entity = null;
        HttpGet method =null;
        CloseableHttpResponse response = null;

        try {
            StringBuilder sb = new StringBuilder();
            if (paramsMap != null) {
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    sb.append("&" + param.getKey() + "=" + param.getValue());
                }
                url = url + "?" + sb.toString().substring(1);
            }

            method = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT)
                    .setSocketTimeout(SOCKETCOOECTION_TIMEOUT).build();// 设置请求超时时间
            method.setConfig(requestConfig);
            response = client.execute(method);
            entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity,"utf-8");
            }
            if (response.getStatusLine().getStatusCode() != NORMAL){
                log.info("返回状态码不是成功状态");
                return null;
            }
            if (response != null) {
                response.close();
            }
        } catch (Exception e) {
            log.info("http请求异常",e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(method!=null){
                method.abort();
            }
        }
        return responseText;
    }

    public static String get(String url, NameValuePair[] nameValuePair){
        Map<String, String> paramsMap = new HashMap<String, String>();
        for (NameValuePair t : nameValuePair) {
            paramsMap.put(t.getName(), t.getValue());
        }
        return get(url, paramsMap);
    }

    public static String post(String url, NameValuePair[] nameValuePair) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        for (NameValuePair t : nameValuePair) {
            paramsMap.put(t.getName(), t.getValue());
        }
        return post(url, paramsMap);
    }

    public static String post(String url, Map<String, String> paramsMap){
        // reuse httpclient to keepalive to the server
        // keepalive in https will save time on tcp handshaking.
        if (null == httpClient)
            httpClient = createSSLClientDefault();

        CloseableHttpClient client = httpClient;
        String responseText = "";
        HttpPost method = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKETCOOECTION_TIMEOUT).build();// 设置请求超时时间
        method.setConfig(requestConfig);
        HttpEntity entity = null;
        CloseableHttpResponse response = null;
        try {
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, "utf-8"));
            }
            response = client.execute(method);
            entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity,"utf-8");
            }

            if (response != null) {
                response.close();
            }
            if (response.getStatusLine().getStatusCode() != NORMAL){
                log.info("返回状态码不是成功状态");
                return null;
            }

        } catch (Exception e) {
            log.info("http请求异常",e);

        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(method!=null){
                method.abort();
            }
        }
        return responseText;
    }


    public static String postJson(String url, Map<String, Object> paramsMap){
        // reuse httpclient to keepalive to the server
        // keepalive in https will save time on tcp handshaking.
        if (null == httpClient)
            httpClient = createSSLClientDefault();

        CloseableHttpClient client = httpClient;
        String responseText = "";
        HttpPost method = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKETCOOECTION_TIMEOUT).build();// 设置请求超时时间
        method.setConfig(requestConfig);
        HttpEntity entity = null;
        CloseableHttpResponse response = null;
        try {
            if (paramsMap != null) {
                JSONObject jsonObject = new JSONObject();
                for (Map.Entry<String, Object> param : paramsMap.entrySet()) {
                    jsonObject.put(param.getKey(),param.getValue());
                }
                StringEntity se = new StringEntity(jsonObject.toJSONString(), "utf-8");
                method.setEntity(se);
            }
            response = client.execute(method);
            entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }

            if (response != null) {
                response.close();
            }
            if (response.getStatusLine().getStatusCode() != NORMAL){
                log.info("返回状态码不是成功状态");
                return null;
            }

        } catch (Exception e) {
            log.info("http请求异常",e);

        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(method!=null){
                method.abort();
            }
        }
        return responseText;
    }

    public static String getForheader(String url, Map<String, String> header, Map<String, String> paramsMap)  {

        if (null == httpClient)
            httpClient = createSSLClientDefault();

        CloseableHttpClient client = httpClient;
        String responseText = null;
        HttpEntity entity = null;
        HttpGet method =null;
        CloseableHttpResponse response = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (paramsMap != null) {
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    sb.append("&" + param.getKey() + "=" + param.getValue());
                }
                url = url + "?" + sb.toString().substring(1);
            }

            method = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT)
                    .setSocketTimeout(SOCKETCOOECTION_TIMEOUT).build();// 设置请求超时时间
            method.setConfig(requestConfig);
            //设置header
            if (header != null) {
                for (Map.Entry<String, String> param : header.entrySet()) {
                    method.setHeader(param.getKey(),param.getValue());
                }
            }
            response = client.execute(method);
            entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
            if (response.getStatusLine().getStatusCode() != NORMAL){
                log.info("返回状态码不是成功状态");
                return null;
            }

            if (response != null) {
                response.close();
            }
        } catch (Exception e) {
            log.info("http请求头异常",e);

        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(method!=null){
                method.abort();
            }
        }
        return responseText;
    }

    /**
     * 得到http post请求
     * @param requestUrl
     * @param mode
     * @return
     */
    public static String getHttpRequest(String requestUrl,String mode){
        String res = "";
        java.io.BufferedReader in = null;
        try {
            URL url = new URL(requestUrl);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(mode);
             in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line+"\n";
            }

        } catch (Exception e) {
            log.error("http post 请求失败，链接是："+requestUrl);
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return res;
    }
}
