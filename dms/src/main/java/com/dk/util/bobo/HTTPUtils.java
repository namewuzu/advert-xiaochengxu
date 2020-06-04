package com.dk.util.bobo;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;

import java.io.IOException;
import java.nio.charset.Charset;

public abstract class HTTPUtils {
    public static HttpResponse getRawHtml(HttpClient client, String personalUrl,String param) {
        //获取响应文件，即html，采用get方法获取响应数据
        // 构建消息实体
        HttpPost post = new HttpPost(personalUrl);
        StringEntity entity = new StringEntity(param,Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        entity.setContentType("application/x-www-form-urlencoded");
        post.setEntity(entity);
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
                HttpStatus.SC_OK, "OK");
        try {
            //执行get方法
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            // getMethod.abort();
        }
        return response;
    }

}