package com.dk.util.bobo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dk.entity.bobo.VideoInfo;
import com.dk.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class ParseUtil {

    public static List<VideoInfo> getDataIndex (String html) throws Exception{
        //获取的数据，存放在集合中
        List<VideoInfo> data = new ArrayList<VideoInfo>();
        //解析json
        JSONObject jsonData = JSONObject.parseObject(html);
        if(jsonData==null){
            return null;
        }
        String msg = jsonData.getString("msg");
        if(StringUtils.isBlank(msg) || !msg.equals("ok")){
            return  null;
        }
        //获取有用的信息
        JSONObject dataInfos = jsonData.getJSONObject("data");
        if(dataInfos==null){
            return null;
        }
        JSONArray videos = dataInfos.getJSONArray("videos");
        //遍历数据
        for(int i=0;i<videos.size();i++){
            JSONObject job = videos.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
            VideoInfo model = parseJson(job);
            data.add(model);
        }
        //返回数据
        return data;
    }

    /**
     * 解析json对象得到结果
     * @param job
     * @return
     */
    public  static  VideoInfo parseJson(JSONObject job) {
        VideoInfo baseModel = new VideoInfo();
        JSONObject video = job.getJSONObject("video");
        JSONObject user = job.getJSONObject("user");
        JSONArray playurlArray = job.getJSONArray("playurl");

        if(video!=null){
            baseModel.setVideoId(video.getString("videoId"));
            baseModel.setTitle(video.getString("title"));
            baseModel.setImgUrl(video.getString("logo"));
            baseModel.setDuration(video.getString("duration"));
        }

        if(user!=null){
            baseModel.setUserId(user.getString("userId"));
            baseModel.setNickName(user.getString("nickName"));
            baseModel.setUserIcon(user.getString("userIcon"));
        }
        if(playurlArray!=null){
            JSONObject playurl = playurlArray.getJSONObject(0);
            baseModel.setVideoUrl(playurl.getString("url"));
            baseModel.setFormat(playurl.getString("format"));
        }
        return baseModel;
    }
}