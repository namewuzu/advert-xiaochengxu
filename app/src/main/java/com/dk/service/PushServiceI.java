package com.dk.service;

import com.dk.entity.Push;
import com.dk.service.base.IBaseService;


public interface PushServiceI extends IBaseService<Push>{

    void pushFormId(Push push)throws Exception;
}
