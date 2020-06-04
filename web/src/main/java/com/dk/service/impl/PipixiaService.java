package com.dk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dk.entity.Vedio;
import com.dk.mapper.DictMapper;
import com.dk.mapper.VedioMapper;
import com.dk.service.CategoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import util.spliter.ParseUrl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzu on 2019/6/24.
 */
public class PipixiaService {
    private static  String spliterRedisKey = "spliter_data_list";
    @Autowired
    CategoryServiceI categoryServiceI;
    @Autowired
    VedioMapper vedioMapper;
    @Autowired
    DictMapper dictMapper;

    public  void getData()throws Exception{
        String baseUrl = "http://lf.snssdk.com/bds/feed/stream/?api_version=3&cursor=1561370728195&list_type=4&feed_count=25&auto_play=1&direction=2&iid=76337533131&device_id=66329100193&ac=wifi&channel=oppo&aid=1319&app_name=super&version_code=192&version_name=1.9.2&device_platform=android&ssmix=a&device_type=PBFM00&device_brand=OPPO&language=zh&os_api=27&os_version=8.1.0&openudid=ddd7dc4dac675064&manifest_version_code=192&resolution=720*1424&dpi=320&update_version_code=1928&_rticket=1561371832486&carrier_region=CN&app_language=ZH&app_region=CN&sys_region=CN&time_zone=Asia%2FShanghai&ts=1561371832&as=a2b52a11184b0dc4e08299&cp=a3b2dd5c840d1d44e2]oKw&mas=00bc1959f377872a8a82cb4d01066dfe90a3a37323339913f91933";
        Map<String,String> head = new HashMap<>();
        head.put("User-Agent","Fiddler");
        head.put("Host","lf.snssdk.com");
        String htmlContent = ParseUrl.clawerHearder(baseUrl,head);
        //System.out.println(htmlContent);
        //解析爬取的数据
        parseData(htmlContent);
    }

    public  void parseData(String htmlContent) {
        JSONObject resjson = JSON.parseObject(htmlContent);
        JSONArray array = resjson.getJSONObject("data").getJSONArray("data");
        System.out.println("array:--"+array);
        for(int i = 1; i < array.size(); i++){
            JSONObject enjson = array.getJSONObject(i);
            String category = "精选";//分类设置为精选
            JSONObject obj = enjson.getJSONObject("item");
            JSONObject share = obj.getJSONObject("share");//视频对象1
            String  title = share.getString("title");//视频标题
            String  videoImg = share.getString("image_url");//视频图形地址

            JSONObject author = obj.getJSONObject("author");//视频对象1
            String  authorname = author.getString("name");//视频标题
            String  authorurl = author.getJSONObject("avatar").getJSONArray("url_list").getJSONObject(0).getString("url");//视频图形地址

            JSONObject vedioObj = obj.getJSONObject("video").getJSONObject("video_download");//视频对象2
            String  vedioUrl = vedioObj.getJSONArray("url_list").getJSONObject(0).getString("url");//视频地址

            System.out.println( "1111111111111111111"+vedioUrl);
            Date date = enjson.getDate("display_time");

            Vedio vedio = new Vedio();
            vedio.setVedioUrl(vedioUrl);
            vedio.setVedioTitle(title);
            vedio.setAuthorName(authorname);
            vedio.setAuthorUrl(authorurl);
            vedio.setCategory(category);
            vedio.setVedioData(date);
            //vedio.setPlayTime(playtimes);
            vedio.setCreateTime(new Date());
            vedio.setVedioImg(videoImg);
            vedio.setCategoryId("39756287088");
            vedio.setPointCount(0);
            vedio.setCommentCount(0);
            vedio.setShareCount(0);
            vedio.setDeleteStatus((byte) 1);
            vedio.setCollectCount(0);
            vedioMapper.insertSelective(vedio);
            vedio.setDetailLink("pages/details/details?offSwitch=false&videoid="+vedio.getId());
            vedioMapper.updateByPrimaryKeySelective(vedio);
        }
    }

    public static void main(String[] args) throws Exception {
        //getData();
    }




}
