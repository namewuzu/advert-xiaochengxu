package com.dk.service;

import com.dk.entity.Dict;
import com.dk.service.base.IBaseService;

import java.util.List;

public interface DictServiceI extends IBaseService<Dict>{

    List<Dict> selectAll();

    void updateDbAndRedis(Dict dict);
}
