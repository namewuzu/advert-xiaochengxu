package com.dk.controller.content;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Content;
import com.dk.entity.ContentShare;
import com.dk.entity.ResultBody;
import com.dk.service.ContentServiceI;
import com.dk.service.ContentShareServiceI;
import com.dk.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 祝福素材分类控制类
 * Created by wuzu on 2019/6/28.
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    private Logger log = LoggerFactory.getLogger(ContentController.class);
    @Autowired
    private ContentServiceI contentServiceI;
    @Autowired
    private ContentShareServiceI contentShareServiceI;
    /**
     * 获取祝福素材列表
     * @return
     */
    @RequestMapping("/getContentListByType")
    @ResponseBody
    public ResultBody<PageResult<Content>>  getContentListByType(PageResult<Content> page,Content content){
        ResultBody<PageResult<Content>> result = new ResultBody("获取获取祝福素材列表成功",200);
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        try {
            //第一个页面随机加载数据
            if(pageNo==1){
                Integer count = contentServiceI.getCountByType(content.getTypeFlag());
                if(count!=null){
                    Random random = new Random();
                    int countRondom = random.nextInt(count-pageSize);
                    List<Content> list = contentServiceI.getRandomPageByType(countRondom,pageSize,content.getTypeFlag());
                    page.setPageNo(1);
                    page.setPageSize(pageSize);
                    page.setDataList(list);
                    page.setTotal(count);
                    result.setObj(page);
                    return result;
                }
            }
            contentServiceI.queryByPage(page,content);
            result.setObj(page);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("获取获取祝福素材列表失败",500);
            log.info("获取获取祝福素材列表失败",e);
            return result;
        }
    }

    /**
     * 根据id获取祝福素材详情信息
     * @param content
     * @return
     */
    @RequestMapping("/getContentById")
    @ResponseBody
    public ResultBody<Content>  getContentById(Content content){
        ResultBody<Content> result = new ResultBody("获取单个祝福成功",200);
        if(content.getId()==null){
            result.setMsgAndStatus("id不能为空",500);
            return result;
        }
        try {
            Content vo  = contentServiceI.selectByPrimaryKey(content.getId());
            result.setObj(vo);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("获取单个祝福失败",500);
            log.info("获取单个祝福失败",e);
            return result;
        }
    }

    /**
     * 分享祝福调用接口
     * @return
     */
    @RequestMapping("/shareContent")
    @ResponseBody
    public ResultBody  shareContent(ContentShare contentShare){
        ResultBody result = new ResultBody("分享祝福页面成功",200);
        try {
            contentShare.setCreateTime(new Date());
            contentShareServiceI.insertSelective(contentShare);
            return result;
        } catch (Exception e) {
            result.setMsgAndStatus("分享祝福页面失败",500);
            log.info("分享祝福页面失败",e);
            return result;
        }
    }

}
