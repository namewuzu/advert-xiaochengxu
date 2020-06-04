package com.dk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dk.entity.Category;
import com.dk.entity.Dict;
import com.dk.entity.Vedio;
import com.dk.mapper.DictMapper;
import com.dk.mapper.VedioMapper;
import com.dk.service.CategoryServiceI;
import com.dk.util.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * 一点资讯爬虫
 * Created by wuzu on 2019/6/15.
 */
@Service
public class YidianzixunService {
    private static  String spliterRedisKey = "spliter_data_list";
    @Autowired
    CategoryServiceI categoryServiceI;
    @Autowired
    VedioMapper vedioMapper;
    @Autowired
    DictMapper dictMapper;

    public List<Vedio> setInter()throws Exception{
        String urlPost = "http://a1.go2yd.com";
        String cookie = "JSESSIONID=KCHflgJkjEUDQS7ZN1qgPw";
        String imgPath = "http://i1.go2yd.com/image/";
        String url = urlPost + "/Website/channel/news-list-for-channel";
        String param = "eventid=81016413314a27af2-b177-43d7-9b4d-02f60e162916&os=27&cstart=0&signature=lAe57NEHut0aEJ2QDOuRf3jN7uWS2-da06M7FjykGG6Qxmd4TaAwXbRw0rvJ2ey0M4KsHF6F7UfY-uX73q43Wz169S7RrAalhmWqSgyegmFmhDDQPsEwXietQNLhSe0zq9QnWsZ6ikMowSGUkl6KVJAVZs3EptohnVh1cJK9zT4&infinite=true&searchentry=channel_navibar&refresh=1&group_fromid=g184&distribution=oppo_preload&version=022700&platform=1&ad_version=010964&reqid=decmn9fg_1555907187430_911&cv=4.9.4.2&cend=30&appid=yidian&switch_local=false&fields=docid&fields=date&fields=image&fields=image_urls&fields=like&fields=source&fields=title&fields=url&fields=comment_count&fields=up&fields=down&brand=OPPO&androidId=b040f7f37acdf812a4d107076f2bf748&net=wifi&rr=ConscryptFileDescriptorSocket.java_219_decmn9fg_1555907187220_911";
        String paramJson = "{\"clientInfo\":{\"deviceInfo\":{\"model\":\"VKY-AL00\",\"device\":\"HWVKY\",\"androidVersion\":\"8.0.0\",\"screenWidth\":1080,\"screenHeight\":1920,\"ppi\":480,\"brand\":\"HUAWEI\",\"manufacturer\":\"HUAWEI\",\"UA\":\"Dalvik\\/2.1.0 (Linux; U; Android 8.0.0; VKY-AL00 Build\\/HUAWEIVKY-AL00)\"},\"userInfo\":{\"imei\":\"7ad9170b5c1d4b3e49cfe546eb3e192b\",\"mac\":\"54:25:EA:65:20:01\",\"language\":\"zh\",\"country\":\"CN\",\"serviceProvider\":\"中国电信\",\"appVersion\":\"4.9.4.2\",\"androidId\":\"a11d9fe476f30d8c\",\"region\":\"%E6%B9%96%E5%8D%97%E7%9C%81%2C%E9%95%BF%E6%B2%99%E5%B8%82%2C%E5%BC%80%E7%A6%8F%E5%8C%BA%2C%E6%B9%96%E5%8D%97%E7%9C%81%E9%95%BF%E6%B2%99%E5%B8%82%E5%BC%80%E7%A6%8F%E5%8C%BA%E6%99%B4%E5%B2%9A%E8%B7%AF%E9%9D%A0%E8%BF%91%E5%8C%97%E8%BE%B0%E4%B8%89%E8%A7%92%E6%B4%B2%28%E5%9C%B0%E9%93%81%E7%AB%99%29\",\"cityCode\":\"0731\",\"adCode\":\"430105\",\"GPS\":\"28.2368495,112.9837459\",\"businessarea\":[],\"AOI\":[{\"id\":\"B0FFHMO50L\",\"name\":\"Hipark凤凰海购物公园\",\"location\":\"28.236787,112.982945\",\"area\":26471.82}]}}}";
        // 配置分类
        List<Category> categoryList = categoryServiceI.selectAll();
        Integer count = 1;
        Dict dict  = dictMapper.selectBySkey((byte) 5);
        if(dict!=null){
            count = Integer.parseInt(dict.getVal());
        }
        List<Vedio> list = new ArrayList<>();
        for(int i = 1; i<= count; i++){
            if(categoryList!=null && categoryList.size()>0){
                for(Category category:categoryList){
                    String channelId = category.getId();
                    if(channelId.equals("29756267388") || channelId.equals("29756267999") || channelId.equals("59756267088")){
                        continue;
                    }
                    //String channelId = "29756267061";
                    System.out.println(channelId);
                    String relUrl = url + "?channel_id=" + channelId + "&" + param;
                    JSONObject json = JSON.parseObject(paramJson);
                    System.out.println(json);
                    System.out.println(relUrl);
                    try {
                        getInfo(relUrl, paramJson, cookie, channelId, imgPath,list);
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
        //放入redis缓存
        RedisPoolUtil.setListEx(spliterRedisKey,3600,list);
        return  list;
    }
    public synchronized void getInfo(String url,String param,String cookie,
                                     String channelId,String imgPath,List<Vedio> list){
        String res = HttpUtils.sendPost(url,param,cookie);
        System.out.println(res);

        JSONObject resjson = JSON.parseObject(res);
        JSONArray array = resjson.getJSONArray("result");
        System.out.println("array:--"+array);
        System.out.println();
        for(int i = 1; i < array.size(); i++){
            JSONObject enjson = array.getJSONObject(i);
            String category = enjson.getString("category");//分类
            JSONArray videoUrls = enjson.getJSONArray("video_urls");//视频地址
            String vedioUrl = videoUrls.getJSONObject(0).getString("url");
            String videoImg = imgPath + enjson.getString("image");//视频地址
            String title = enjson.getString("title");//视频标题
            JSONObject wemediaJson = enjson.getJSONObject("wemedia_info");
            String authorurl = wemediaJson.getString("image");//作者头像地址
            String authorname = wemediaJson.getString("name");//作者名称
            String userid = wemediaJson.getString("userid");//作者id
            Date date = enjson.getDate("date");//视频日期
            Integer playtimes = enjson.getInteger("duration");//播放时间

            System.out.println(category + "     " + vedioUrl + "     " + title + "    " + authorurl + "     " + authorname + "      " + userid + "      " + date + "   " + playtimes);

            Vedio vedio = new Vedio();
            vedio.setVedioUrl(vedioUrl);
            vedio.setVedioTitle(title);
            vedio.setAuthorName(authorname);
            vedio.setAuthorUrl(authorurl);
            vedio.setCategory(category);
            vedio.setAutorUserId(userid);
            vedio.setVedioData(date);
            vedio.setPlayTime(playtimes);
            vedio.setCreateTime(new Date());
            vedio.setVedioImg(videoImg);
            vedio.setCategoryId(channelId);
            vedio.setPointCount(0);
            vedio.setCommentCount(0);
            vedio.setShareCount(0);
            vedio.setDeleteStatus((byte) 1);
            //放入list,最后放入缓存
            list.add(vedio);
            //vedioMapper.insertSelective(vedio);
            //vedio.setDetailLink("pages/details/details?offSwitch=false&videoid="+vedio.getId());
            //vedioMapper.updateByPrimaryKeySelective(vedio);
        }
    }

    /**
     * 获取缓存中的爬取的视频
     * @return
     */
    public List<Vedio> getSpliterVedios() throws Exception{
        List<Vedio> list = RedisPoolUtil.getList(spliterRedisKey);
        return list;
    }

    public void spliteVediosAdd(List<Vedio> vedios) throws Exception{
            vedioMapper.insertForeach(vedios);
            RedisPoolUtil.remove(spliterRedisKey);
    }
}
