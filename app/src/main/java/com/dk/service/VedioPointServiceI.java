package com.dk.service;

import com.dk.entity.VedioPoint;
import com.dk.service.base.IBaseService;

public interface VedioPointServiceI extends IBaseService<VedioPoint>{

    void userPointVedio(Integer id, Integer userId, Integer vedioId);
}
