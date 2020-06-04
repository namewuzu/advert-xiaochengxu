package com.dk.vo;

/**
 * Created by wuzu on 2020/3/27.
 */
public class AdvertVo {

    private Integer id;

    private Integer width;

    private Integer hegiht;

    private String imgUrl;

    private String pageUrl;

    private String name;

    private Byte flag;

    private Byte advertType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHegiht() {
        return hegiht;
    }

    public void setHegiht(Integer hegiht) {
        this.hegiht = hegiht;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    public Byte getAdvertType() {
        return advertType;
    }

    public void setAdvertType(Byte advertType) {
        this.advertType = advertType;
    }
}
