package com.dk.service;

import com.dk.entity.ResultBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wuzu on 2019/5/8.
 */
public interface WeixinPayService {
    ResultBody reOrder(HttpServletRequest request, HttpServletResponse response, String code,
                       String ip, Integer fee,Integer userId)throws Exception;

    void notity(HttpServletResponse response, HttpServletRequest request) throws Exception;

    void failOrder(String orderNo);
}
