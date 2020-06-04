package com.dk.service;

import com.dk.entity.VedioPlay;
import com.dk.service.base.IBaseService;

public interface VedioPlayServiceI extends IBaseService<VedioPlay>{

    void userPlayVedio(Integer userId, Integer vedioId)throws Exception;
}
