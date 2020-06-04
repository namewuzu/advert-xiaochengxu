package com.dk.mapper;

import com.dk.entity.Vedio;
import com.dk.mapper.base.IBaseDao;
import com.dk.vo.VedioVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VedioMapper extends IBaseDao<Vedio>{
    int deleteByPrimaryKey(Integer id);

    int insert(Vedio record);

    int insertSelective(Vedio record);

    Vedio selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Vedio record);

    int updateByPrimaryKey(Vedio record);

    List<Vedio> selectNextTen(@Param("start") int start,@Param("num")  int num);

    List<VedioVo> getVoPage(@Param("vedio") VedioVo vedio, @Param("sort")Map<String,Integer> sort);

    void updateTodayData(@Param("startDate")String todayString,@Param("endDate") String todayString1);

    // 新增根据主键ID来更新视频权重
    //void updateVedioWeight(Integer id);

    void insertForeach(List<Vedio> vedios);

    // 删除那些分享/播放、点赞/播放、评论/播放、收藏/播放同时为0的视频
    int cleanVedios();

    // 查询需要删除的视频
    List<Vedio> findNeedDeleteVedios();

    // 根据多个视频ID来删除视频
    int deleteVediosByIds(@Param("ids") List<Integer> ids);
}