package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Vedio;
import com.dk.mapper.VedioMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.VedioServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.util.PageResult;
import com.dk.vo.VedioVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class VedioServiceImpl extends BaseServiceImpl<Vedio> implements VedioServiceI {
    private Logger log = LoggerFactory.getLogger(VedioServiceImpl.class);
    @Autowired
    private VedioMapper vedioMapper;

    @Override
    protected IBaseDao<Vedio> getMapper() {
        return vedioMapper;
    }

    @Override
    public PageResult<VedioVo> queryByVoPage(PageResult<VedioVo> page, VedioVo vedio) {
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        pageNo = pageNo == 0 ? 1 : pageNo;
        pageSize = pageSize == 0 ? 10 : pageSize;
        PageHelper.startPage(pageNo, pageSize);
        return PageResult.toPageResult(getVoPage(vedio), page);
    }

    public List<VedioVo> getVoPage(VedioVo vedio) {
        //排序字段处理
        Map<String, Integer> sort = new HashMap<>();
        if (vedio.getShareFlag() != null) {
            sort.put("share_count", vedio.getShareFlag());
        }
        if (vedio.getPointFlag() != null) {
            sort.put("point_count", vedio.getPointFlag());
        }
        if (vedio.getCommentFlag() != null) {
            sort.put("comment_count", vedio.getCommentFlag());
        }
        if (vedio.getCollectFlag() != null) {
            sort.put("collect_count", vedio.getCollectFlag());
        }
        if (vedio.getPlayFlag() != null) {
            sort.put("play_count", vedio.getPlayFlag());
        }
        if(vedio.getWeightFlag() != null) {
            sort.put("weight", vedio.getWeightFlag());
        }
        List<VedioVo> list = new ArrayList<>();
        try {
            list = vedioMapper.getVoPage(vedio, sort);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return list;
    }

    // 删除那些分享/播放、点赞/播放、评论/播放、收藏/播放同时为0的视频
    @Override
    public int cleanVedios() {
        return vedioMapper.cleanVedios();
    }

    // 分页查询需要删除的视频
    @Override
    public PageResult<Vedio> findNeedDeleteVedios(PageResult<Vedio> pageResult) {
        int pageNo = pageResult.getPageNo();
        int pageSize = pageResult.getPageSize();
        pageNo = pageNo == 0 ? 1 : pageNo;
        pageSize = pageSize == 0 ? 10 : pageSize;
        PageHelper.startPage(pageNo, pageSize);
        return PageResult.toPageResult(vedioMapper.findNeedDeleteVedios(), pageResult);
    }

    // 根据多个视频ID来删除视频
    @Override
    public int deleteVediosByIds(List<Integer> ids) {
        return vedioMapper.deleteVediosByIds(ids);
    }

}