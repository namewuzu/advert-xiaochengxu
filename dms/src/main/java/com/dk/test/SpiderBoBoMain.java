package com.dk.test;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.dk.entity.bobo.VideoInfo;
import com.dk.util.bobo.URLFecter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;


public class SpiderBoBoMain {
    //log4j的是使用，不会的请看之前写的文章
    static final Log logger = LogFactory.getLog(SpiderBoBoMain.class);
    public static void main(String[] args) throws Exception {
        //初始化一个httpclient
        HttpClient client = new DefaultHttpClient();
        //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
        String url="http://api.lite.miaopai.com/v1/recommend/index.json";
        String param="_aKey=ANDROID&_abId=574-158%2C555-154%2C519-102%2C457-148%2C436-144%2C406-142%2C150-119%2C69-110&_androidID=b829469687af8a7b&_appName=%E6%B3%A2%E6%B3%A2%E8%A7%86%E9%A2%91&_carrier=%E7%A7%BB%E5%8A%A8&_cpu=arm64-v8a&_cpuId=MT6755&_dId=OPPO%2BR9m&_devid=9DD660746D9FF3709C4B299B8E89C88A&_facturer=OPPO&_imei=863048038213158&_lac=-1&_lang=zh_CN&_latitude=0.0&_localIp=192.168.124.11&_longitude=0.0&_mac=cc%3A2d%3A83%3A2d%3A4e%3A70&_nId=1&_pName=tv.yixia.bobo&_pcId=oppo_market&_pgLoad=0&_px=1080x1920&_reqId=71E598CDD4D86CD003B49988D4D83142&_reqNum=0&_rt=0&_sessionId=3aea9f99aa9e3f587c3416a509c2c611&_sign=05ea92ca7c5a498cfdfe&_t=1521877402&_token=&_uId=0&_udid=6BFF5E4771E389844669B54BEAB676DA&_umengId=d77c38a23e7e8a40bc708dbb7bb4614b&_vApp=7807&_vName=2.5.6&_vOs=5.1&cateId=110&country=CN&debug=0&ifMoment=0&lastUpdateTime=1521877393&newinstall=0&page=1&size=10&type=up";
        //抓取的数据
        List<VideoInfo> bookdatas= URLFecter.URLParserIndex(client,url,param);
        //循环输出抓取的数据
        for (VideoInfo jd:bookdatas) {
            logger.info("name:"+jd.getTitle()+"\t"+"url:"+jd.getVideoUrl()+"\t"+"imageUrl:"+jd.getImgUrl()+"\t"+"name:"+jd.getNickName()+"\t"+"userId:"+jd.getUserId()+"\t"+"duration="+jd.getDuration());
        }

    }
}