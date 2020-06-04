package com.dk.vo;

import com.dk.entity.Vedio;

/**
 * Created by wuzu on 2019/5/23.
 */
public class VedioVo extends Vedio{
    private Integer shareCountLow;//范围开始
    private Integer shareCountHigh;//范围结束
    private Integer shareFlag;//1-正序，2-倒序
    private Integer pointFlag;//1-正序，2-倒序
    private Integer commentFlag;//1-正序，2-倒序
    private Integer collectFlag;//1-正序，2-倒序
    private Integer playFlag;//1-正序，2-倒序
    private Integer weightFlag; // 权重排序标识，1正序，2倒序

    public Integer getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(Integer shareFlag) {
        this.shareFlag = shareFlag;
    }

    public Integer getPointFlag() {
        return pointFlag;
    }

    public void setPointFlag(Integer pointFlag) {
        this.pointFlag = pointFlag;
    }

    public Integer getCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(Integer commentFlag) {
        this.commentFlag = commentFlag;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }

    public Integer getPlayFlag() {
        return playFlag;
    }

    public void setPlayFlag(Integer playFlag) {
        this.playFlag = playFlag;
    }

    public Integer getShareCountHigh() {
        return shareCountHigh;
    }

    public void setShareCountHigh(Integer shareCountHigh) {
        this.shareCountHigh = shareCountHigh;
    }

    public Integer getShareCountLow() {
        return shareCountLow;
    }

    public void setShareCountLow(Integer shareCountLow) {
        this.shareCountLow = shareCountLow;
    }

    public Integer getWeightFlag() {
        return weightFlag;
    }

    public void setWeightFlag(Integer weightFlag) {
        this.weightFlag = weightFlag;
    }
}
