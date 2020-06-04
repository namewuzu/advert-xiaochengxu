package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Advert;
import com.dk.entity.Vedio;
import com.dk.mapper.AdvertMapper;
import com.dk.mapper.VedioMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.VedioServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.*;
import com.dk.vo.VedioInforVo;
import com.dk.vo.VedioTypeVo;
import com.dk.vo.VedioVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class VedioServiceImpl extends BaseServiceImpl<Vedio> implements VedioServiceI {
    private Logger log = LoggerFactory.getLogger(VedioServiceImpl.class);
    @Autowired
    private VedioMapper vedioMapper;
    @Autowired
    private AdvertMapper advertMapper;

    @Override
    protected IBaseDao<Vedio> getMapper() {
        return vedioMapper;
    }


    /**
     * vo 数据分页
     * @param page
     * @param vo
     * @return
     */
    @Override
    public PageResult getVoPage(PageResult page, VedioVo vo) throws Exception{
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        //推荐视频
        if(vo.getCategoryId().equals(AppConstant.RECOMMEND_CATEGORYID)){
            log.info("***********************11111111111111111111111111111111111111111111111");
            //获取redis存储的播放量标准值
            Integer playCount = Integer.valueOf(RedisPoolUtil.getString(RedisConstanct.RECOMMEND_PLAY_COUNT));
            if(playCount==null){
                playCount = 0;
            }
            Integer count = vedioMapper.getRecommendCount(playCount);
            if(count!=null){
                Random random = new Random();
                int countRondom = random.nextInt(count-pageSize);
                List<VedioVo> list = vedioMapper.getRecommendPage(countRondom,pageSize,vo.getUserId(),playCount);
                page.setPageNo(1);
                page.setPageSize(pageSize);
                page.setDataList(list);
                page.setTotal(count);
                log.info("*********************************推荐视频随机分页****"+count+"****"+vo.getUserId()+"***"+countRondom);
                return page;
            }
        }
        //随机获取pageSize条数据
        if(page.getPageNo()==1){
            log.info("***********************222222222222222222222222222222222222222222222222222");
            //获取redis存储的今日分享量标准值
            Integer shareCount = Integer.valueOf(RedisPoolUtil.getString(RedisConstanct.SHARE_WEIGHT_TUI));
            if(shareCount==null){
                shareCount = 0;
            }
            Integer count = vedioMapper.selectCountByCategoryId(vo.getCategoryId(),shareCount);
            if(count!=null){
                if(count<=pageSize){
                    count = vedioMapper.selectCountByCategoryId(vo.getCategoryId(),null);
                    shareCount =0;
                }
                Random random = new Random();
                int countRondom = random.nextInt(count-pageSize);
                List<VedioVo> list = vedioMapper.selectRandomByCategoryId(vo.getCategoryId(),countRondom,pageSize,vo.getUserId(),shareCount);
                page.setPageNo(1);
                page.setPageSize(pageSize);
                page.setDataList(list);
                page.setTotal(count);
                return page;
            }
        }
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getFontPage(vo),page);
    }


    @Override
    public List<Vedio> getRandomVedios() throws Exception{
        Integer shareCount = Integer.valueOf(RedisPoolUtil.getString(RedisConstanct.SHARE_WEIGHT_TUI));
        if(shareCount==null){
            shareCount = 0;
        }
        List<Vedio> list = new ArrayList<>();
        Vedio vedio = new Vedio();
        /*for(int i=0;i<Constants.randomCount;i++){*/
        list = vedioMapper.getRandomVedios(shareCount);
          /*  log.info("....................vedioId="+vedio.getId());
            if(vedio!=null){
                list.add(vedio);
            }
            vedio=null;*/
        /*}*/
        //添加广告一条随机详情广告
       /* Advert advert = advertMapper.getOneRandomAdvert(2);
        if(advert!=null){
            vedio.setImgUrl(advert.getImgUrl());
            vedio.setPageUrl(advert.getPageUrl());
            vedio.setName(advert.getName());
            vedio.setFlag(advert.getFlag());
            vedio.setAdvertType(advert.getAdvertType());
            list.add(vedio);
        }*/
        return list;
    }

    @Override
    public VedioInforVo getVedioById(Integer vedioId,Integer userId) throws Exception{
        return vedioMapper.getVedioById(vedioId,userId);
    }

    @Override
    public PageResult queryTypeByPage(PageResult<VedioTypeVo> page, VedioTypeVo vedioTypeVo) throws Exception{
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        switch(vedioTypeVo.getType()){
            case 1:
                page =  PageResult.toPageResult(getShareDataPage(vedioTypeVo),page);
                break;
            case 2:
                page =  PageResult.toPageResult(getPointDataPage(vedioTypeVo),page);
                break;
            case 3:
                page =  PageResult.toPageResult(getCommentDataPage(vedioTypeVo),page);
                break;
            case 4:
                page =  PageResult.toPageResult(getPlayDataPage(vedioTypeVo),page);
                break;
            case 5:
                page =  PageResult.toPageResult(getCollectDataPage(vedioTypeVo),page);
                break;
        }
        return page;
    }

    /**
     * 根据视频随机获取8条视频数据 (分页)
     * @return
     */
    @Override
    public PageResult getRandomVoPage(PageResult page, VedioVo vo) {
        int pageNo=page.getPageNo();
        int pageSize=page.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?8:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getRandomFontPage(vo),page);
    }

    @Override
    public void addCommentCount(Integer vedioId) {
        vedioMapper.addCommentCount(vedioId);
    }

    public List getRandomFontPage(VedioVo vo) {
        List<VedioVo> list = new ArrayList<>();
        try {
            list = vedioMapper.getRandomFontPage(vo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * 评论数据
     * @param vedioTypeVo
     * @return
     */
    public List<VedioTypeVo> getCommentDataPage(VedioTypeVo vedioTypeVo) {
        List<VedioTypeVo> list = new ArrayList<>();
        try {
            list = vedioMapper.getCommentVoPage(vedioTypeVo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * 播放数据
     * @param vedioTypeVo
     * @return
     */
    public List<VedioTypeVo> getPlayDataPage(VedioTypeVo vedioTypeVo) {
        List<VedioTypeVo> list = new ArrayList<>();
        try {
            list = vedioMapper.getPlayVoPage(vedioTypeVo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * 收藏数据
     * @param vedioTypeVo
     * @return
     */
    public List<VedioTypeVo> getCollectDataPage(VedioTypeVo vedioTypeVo) {
        List<VedioTypeVo> list = new ArrayList<>();
        try {
            list = vedioMapper.getCollcettVoPage(vedioTypeVo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * 点赞数据
     * @param vedioTypeVo
     * @return
     */
    public List<VedioTypeVo> getPointDataPage(VedioTypeVo vedioTypeVo) {
        List<VedioTypeVo> list = new ArrayList<>();
        try {
            list = vedioMapper.getPointVoPage(vedioTypeVo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * 分享数据
     * @param vedioTypeVo
     * @return
     */
    public List<VedioTypeVo> getShareDataPage(VedioTypeVo vedioTypeVo) {
        List<VedioTypeVo> list = new ArrayList<>();
        try {
            list = vedioMapper.getShareVoPage(vedioTypeVo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;

    }


    public List<VedioVo> getFontPage(VedioVo obj)throws Exception{
        List<VedioVo> list = new ArrayList<>();
        try {
            list = vedioMapper.getVoPage(obj);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }
}