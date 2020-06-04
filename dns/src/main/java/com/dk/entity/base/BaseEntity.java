package com.dk.entity.base;

import java.io.Serializable;

/**
 * Created by wuzu on 2017/6/2.
 */
public class BaseEntity implements Serializable {
    private Integer uid;
    private String token;
    private Integer ranking;

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
