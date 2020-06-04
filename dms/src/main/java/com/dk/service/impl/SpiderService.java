package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.bobo.VideoInfo;
import com.dk.mapper.author.AuthorMapper;
import com.dk.mapper.content.ContentMapper;
import com.dk.util.ConfigUtils;
import com.dk.util.StringUtils;
import com.dk.util.bobo.URLFecter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuzu on 2018/3/24.
 */
@Service
public class SpiderService {
    private Logger log = LoggerFactory.getLogger(SpiderService.class);
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private AuthorMapper authorMapper;
    private int times;

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int spiderData(){
        //初始化一个httpclient
        HttpClient client = new DefaultHttpClient();
        //爬取计数器
        int count = 0;
        String url=ConfigUtils.getProperty("spider.url");
        List<VideoInfo> bookdatas= null;
        //获取所有的连接并进行爬取操作
        List<String> urls = getUrls();
        for(int i=1;i<=times;i++){
            for(int x=0;x<urls.size();x++){
                String param = urls.get(x);
                if(StringUtils.isBlank(param)){
                    continue;
                }
                try {
                    bookdatas = URLFecter.URLParserIndex(client,url,param);
                    if(bookdatas!=null && bookdatas.size()>0){
                        count+=bookdatas.size();
                        int u = 0;
                        try {
                            for (u=0;u<bookdatas.size();u++) {
                                VideoInfo jd = bookdatas.get(u);
                                log.info("name:"+jd.getTitle()+"\t"+"url:"+jd.getVideoUrl()+"\t"+"imageUrl:"+jd.getImgUrl()+"\t"+"name:"+jd.getNickName()+"\t"+"userId:"+jd.getUserId());
                                insertVo(jd,x+4);
                            }
                            bookdatas.clear();
                        }catch (Exception e) {
                            log.info("插入失败.................exception",e);
                            throw new RuntimeException();
                        }
                    }
                } catch (Exception e) {
                    log.info("插入失败.................url="+param,e);
                    throw new RuntimeException();
                }
            }
        }
        return  count;
    }

    public void insertVo(VideoInfo jd,int index) throws Exception {
        Content content = new Content();
        if(jd==null){
            return;
        }
        //插入视频内容
        content.setCreateTime(new Date());
        content.setAuthorId(jd.getUserId());
        content.setAuthorName(jd.getNickName());
        content.setDuration(jd.getDuration());
        content.setFormat(jd.getFormat());
        content.setImgageUrl(jd.getImgUrl());
        content.setVedioTitle(jd.getTitle());
        content.setVedioUrl(jd.getVideoUrl());
        content.setVedioClassId(index);
        contentMapper.insertSelective(content);
        //插入作者
        if(StringUtils.isNotBlank(jd.getUserId()) && authorMapper.selectByPrimaryKeyString(jd.getUserId())==null){
            Author author = new Author();
            author.setAuthorName(jd.getNickName());
            author.setAuthorId(jd.getUserId());
            author.setCreateTime(new Date());
            author.setImageUrl(jd.getUserIcon());
            authorMapper.insertSelective(author);
        }
    }

    public List<String> getUrls() {
        List<String> urls = new ArrayList<>();
        urls.add( ConfigUtils.getProperty("spider.funny"));
        urls.add( ConfigUtils.getProperty("spider.music"));
        urls.add( ConfigUtils.getProperty("spider.society"));
        urls.add( ConfigUtils.getProperty("spider.beauty"));
        urls.add( ConfigUtils.getProperty("spider.feature"));
        urls.add( ConfigUtils.getProperty("spider.movies"));
        urls.add( ConfigUtils.getProperty("spider.sketch"));
        urls.add( ConfigUtils.getProperty("spider.life"));
        urls.add( ConfigUtils.getProperty("spider.master"));
        urls.add( ConfigUtils.getProperty("spider.variety"));
        urls.add( ConfigUtils.getProperty("spider.game"));
        urls.add( ConfigUtils.getProperty("spider.novel"));
        urls.add( ConfigUtils.getProperty("spider.military"));
        urls.add( ConfigUtils.getProperty("spider.wild"));
        urls.add( ConfigUtils.getProperty("spider.squaredance"));
        urls.add( ConfigUtils.getProperty("spider.adorable"));
        urls.add( ConfigUtils.getProperty("spider.entertainment"));
        urls.add( ConfigUtils.getProperty("spider.fashion"));
        urls.add( ConfigUtils.getProperty("spider.car"));
        urls.add( ConfigUtils.getProperty("spider.sprots"));
        urls.add( ConfigUtils.getProperty("spider.wealth"));
        return urls;
    }

    public static  void main(String[] args){
        SpiderService spider = new SpiderService();
        System.out.println(spider.spiderData());
    }
}
