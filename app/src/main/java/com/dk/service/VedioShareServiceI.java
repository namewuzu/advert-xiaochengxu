package com.dk.service;

import com.dk.entity.VedioShare;
import com.dk.service.base.IBaseService;

public interface VedioShareServiceI extends IBaseService<VedioShare>{

    void userShareVedio( Integer userId, Integer vedioId,Byte id)throws Exception;
}
