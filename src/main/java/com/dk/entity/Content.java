package com.dk.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Content {
    private Integer id;

    private Date createTime;

    private String vedioUrl;

    private String vedioTitle;

    private String imgageUrl;

    private String duration;

    private String authorId;

    private String vedioId;

    private String authorName;

    private String format;

    private Integer vedioClassId;

    private BigDecimal browsePrice;

    private Integer playCount;

    private Integer browseCount;

    private Integer shareCount;

    private Byte delStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl == null ? null : vedioUrl.trim();
    }

    public String getVedioTitle() {
        return vedioTitle;
    }

    public void setVedioTitle(String vedioTitle) {
        this.vedioTitle = vedioTitle == null ? null : vedioTitle.trim();
    }

    public String getImgageUrl() {
        return imgageUrl;
    }

    public void setImgageUrl(String imgageUrl) {
        this.imgageUrl = imgageUrl == null ? null : imgageUrl.trim();
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    public String getVedioId() {
        return vedioId;
    }

    public void setVedioId(String vedioId) {
        this.vedioId = vedioId == null ? null : vedioId.trim();
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    public Integer getVedioClassId() {
        return vedioClassId;
    }

    public void setVedioClassId(Integer vedioClassId) {
        this.vedioClassId = vedioClassId;
    }

    public BigDecimal getBrowsePrice() {
        return browsePrice;
    }

    public void setBrowsePrice(BigDecimal browsePrice) {
        this.browsePrice = browsePrice;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Byte getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Byte delStatus) {
        this.delStatus = delStatus;
    }
}