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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.HttpUtils;
import util.spliter.ParseUrl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * 一点资讯爬虫
 * Created by wuzu on 2019/6/15.
 */
@Service
public class MeipaiService {
    private Logger log = LoggerFactory.getLogger(MeipaiService.class);
    @Autowired
    CategoryServiceI categoryServiceI;
    @Autowired
    VedioMapper vedioMapper;
    @Autowired
    DictMapper dictMapper;

    public void insertVedio(){
        Integer count = 50;
        String path = "https://www.meipai.com/square/474?";
        for(int i=2;i<=count;i++){
            String url = path+"p="+i;
            try {
                parseListPage(url);
            } catch (Exception e) {
                log.info("爬取视频失败.......................................");
            }
        }
    }

    public  void parseListPage(String url) throws Exception {
        String baseUrl = "https://www.meipai.com";
        String htmlContent = ParseUrl.clawer2(url);
        //System.out.println(htmlContent);
        //使用jSoup解析里头的内容
        //就像操作html doc文档对象一样操作网页中的元素
        Document doc = Jsoup.parse(htmlContent);
        //Element span = doc.select("ul:[id=mediasList").get(0);
        Elements elements = doc.select("li[class=pr no-select loading  J_media_list_item]");
        Iterator it = elements.iterator();
        while(it.hasNext()) {
            try{
                Element element = (Element)it.next();
                //视频首页图片
                Node vedioImgNode= element.child(1);//图片节点
                String vedioImgUrl = vedioImgNode.attr("src");//视频图片
                String vedioTitle = vedioImgNode.attr("alt");//视频标题
                System.out.println("视频图片="+vedioImgUrl);
                System.out.println("视频标题="+vedioTitle);

                //视频详情页面信息
                Node vedioPageNode = element.child(2).childNode(3);
                String vedioPageUrl = baseUrl+vedioPageNode.attr("href");//视频页面地址
                System.out.println("视频页面地址="+vedioPageUrl);

                //用户信息节点
                Node userNode= element.child(3);
                Node userImgNode = userNode.childNode(1).childNode(1);
                String userImg = userImgNode.attr("src");//用户图片
                Node userNameNode  = userNode.childNode(3).childNode(1);
                String userName = userNameNode.attr("title");//用户昵称

                System.out.println("用户图片="+userImg);
                //System.out.println("用户昵称="+userName);

                //获取视频链接地址
                String vedioUrl = ParseUrl.getVedioLink(vedioPageUrl);//视频地址
                System.out.println("视频地址="+vedioUrl);
                Vedio vedio = new Vedio();
                vedio.setVedioImg(vedioImgUrl);
                vedio.setVedioTitle(vedioTitle);
                vedio.setAuthorName(userName);
                vedio.setAuthorUrl(userImg);
                vedio.setVedioUrl(vedioUrl);
                vedio.setCreateTime(new Date());
                vedio.setCategory("美女");
                vedio.setCategory("美女");
                vedio.setPointCount(0);
                vedio.setCommentCount(0);
                vedio.setShareCount(0);
                vedio.setCategoryId("29756267999");
                vedioMapper.insert(vedio);
            }catch (Exception e){
                log.info("爬取一条失败...............................");
                continue;
            }

        }
    }

}
