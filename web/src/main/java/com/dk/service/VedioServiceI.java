package com.dk.service;

import com.dk.entity.Vedio;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.dk.vo.VedioVo;

import java.util.List;

public interface VedioServiceI extends IBaseService<Vedio>{

    PageResult<VedioVo> queryByVoPage(PageResult<VedioVo> page, VedioVo vedio);

    // 删除那些分享/播放、点赞/播放、评论/播放、收藏/播放同时为0的视频
    int cleanVedios();

    // 查询需要删除的视频
    PageResult<Vedio> findNeedDeleteVedios(PageResult<Vedio> pageResult);

    // 根据多个视频ID来删除视频
    int deleteVediosByIds(List<Integer> ids);
}
