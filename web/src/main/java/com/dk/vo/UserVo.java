package com.dk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wuzu on 2019/5/24.
 */
public class UserVo {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Integer startPointCount;
    private Integer endPointCount;
    private Integer startShareCount;
    private Integer endShareCount;
    private Integer startCommentCount;
    private Integer endCommentCount;
    private Integer startPlayCount;
    private Integer endPlayCount;
    private Integer startCollectCount;
    private Integer endCollectCount;
    private String nickName;
    private Integer shareFlag;//1-正序，2-倒序
    private Integer pointFlag;//1-正序，2-倒序
    private Integer commentFlag;//1-正序，2-倒序
    private Integer collectFlag;//1-正序，2-倒序
    private Integer playFlag;//1-正序，2-倒序
    private Integer createTimeFlag; // 用户注册时间排序标识符，1正序，2倒序

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

    public Integer getEndCollectCount() {
        return endCollectCount;
    }

    public void setEndCollectCount(Integer endCollectCount) {
        this.endCollectCount = endCollectCount;
    }

    public Integer getEndCommentCount() {
        return endCommentCount;
    }

    public void setEndCommentCount(Integer endCommentCount) {
        this.endCommentCount = endCommentCount;
    }

    public Date getEndDate() {
        return endDate;
    }


    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getEndPlayCount() {
        return endPlayCount;
    }

    public void setEndPlayCount(Integer endPlayCount) {
        this.endPlayCount = endPlayCount;
    }

    public Integer getEndPointCount() {
        return endPointCount;
    }

    public void setEndPointCount(Integer endPointCount) {
        this.endPointCount = endPointCount;
    }

    public Integer getEndShareCount() {
        return endShareCount;
    }

    public void setEndShareCount(Integer endShareCount) {
        this.endShareCount = endShareCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getStartCollectCount() {
        return startCollectCount;
    }

    public void setStartCollectCount(Integer startCollectCount) {
        this.startCollectCount = startCollectCount;
    }

    public Integer getStartCommentCount() {
        return startCommentCount;
    }

    public void setStartCommentCount(Integer startCommentCount) {
        this.startCommentCount = startCommentCount;
    }

    public Date getStartDate() {
        return startDate;
    }


    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getStartPlayCount() {
        return startPlayCount;
    }

    public void setStartPlayCount(Integer startPlayCount) {
        this.startPlayCount = startPlayCount;
    }

    public Integer getStartPointCount() {
        return startPointCount;
    }

    public void setStartPointCount(Integer startPointCount) {
        this.startPointCount = startPointCount;
    }

    public Integer getStartShareCount() {
        return startShareCount;
    }

    public void setStartShareCount(Integer startShareCount) {
        this.startShareCount = startShareCount;
    }

    public Integer getCreateTimeFlag() {
        return createTimeFlag;
    }

    public void setCreateTimeFlag(Integer createTimeFlag) {
        this.createTimeFlag = createTimeFlag;
    }
}
