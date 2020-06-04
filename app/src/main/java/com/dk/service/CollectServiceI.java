package com.dk.service;

import com.dk.entity.VedioCollect;
import com.dk.service.base.IBaseService;

public interface CollectServiceI extends IBaseService<VedioCollect>{

     void userCollect(Integer userId, Integer vedioId) throws Exception;
}
