/*
package com.dk.util.bobo;

import com.dk.entity.bobo.VideoInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class URLFecter {

    public static List<VideoInfo> URLParserIndex(HttpClient client, String url,String param) throws Exception {
        //用来接收解析的数据
        List<VideoInfo> JingdongData = new ArrayList<VideoInfo>();
        //获取网站响应的html，这里调用了HTTPUtils类
        HttpResponse response = HTTPUtils.getRawHtml(client,url,param);
        //获取响应状态码
        int StatusCode = response.getStatusLine().getStatusCode();
        //如果状态响应码为200，则获取html实体内容或者json文件
        if (StatusCode == 200) {
            String entity = EntityUtils.toString(response.getEntity(), "utf-8");
            JingdongData = ParseUtil.getDataIndex(entity);
            EntityUtils.consume(response.getEntity());
        } else {
            //否则，消耗掉实体
            EntityUtils.consume(response.getEntity());
        }
        return JingdongData;
    }
}*/
