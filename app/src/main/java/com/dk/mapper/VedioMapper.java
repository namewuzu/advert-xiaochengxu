package com.dk.mapper;

import com.dk.entity.Vedio;
import com.dk.mapper.base.IBaseDao;
import com.dk.vo.VedioInforVo;
import com.dk.vo.VedioTypeVo;
import com.dk.vo.VedioVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VedioMapper extends IBaseDao<Vedio>{
    int deleteByPrimaryKey(Integer id);

    int insert(Vedio record);

    int insertSelective(Vedio record);

    Vedio selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Vedio record);

    int updateByPrimaryKey(Vedio record);

    List<Vedio> getRandomVedios(@Param("shareCount") Integer shareCount);

    VedioInforVo getVedioById(@Param("vedioId")Integer vedioId, @Param("userId")Integer userId);

    Integer selectCountByCategoryId(@Param("categoryId")String categoryId,@Param("shareCount")Integer shareCount);

    List<VedioVo> selectRandomByCategoryId(@Param("categoryId")String categoryId,@Param("randomVlaue") Integer countRondom,@Param("randomCount") Integer pageSize,
                                           @Param("userId")Integer userId,@Param("shareCount")Integer shareCount);

    List<VedioTypeVo> getCommentVoPage(VedioTypeVo vedioTypeVo);

    List<VedioTypeVo> getPlayVoPage(VedioTypeVo vedioTypeVo);

    List<VedioTypeVo> getCollcettVoPage(VedioTypeVo vedioTypeVo);

    List<VedioTypeVo> getPointVoPage(VedioTypeVo vedioTypeVo);

    List<VedioTypeVo> getShareVoPage(VedioTypeVo vedioTypeVo);

    List<VedioVo> getVoPage(VedioVo obj);

    void addShareCount(@Param("vedioId") Integer vedioId);

    void addPlayCount(@Param("vedioId") Integer vedioId);

    void addCollectCount(@Param("vedioId")Integer vedioId);

    void addPointCount(@Param("vedioId")Integer vedioId);

    void subPointCount(@Param("vedioId")Integer vedioId);

    void addAllShareCount(@Param("vedioId")Integer vedioId);

    List<VedioVo> getRandomFontPage(VedioVo vo);

    void addCommentCount(@Param("vedioId")Integer vedioId);

    Integer selectCountByShare(@Param("shareCount") Integer shareCount);

    Vedio getOneRandowRecord(@Param("pushShareCount") Integer pushShareCount);

    List<VedioVo> getRecommendPage( @Param("countRondom")int countRondom, @Param("pageSize")int pageSize,
                                    @Param("userId") Integer userId,@Param("playCount") Integer playCount);

    Integer getRecommendCount(@Param("playCount") Integer playCount);
}