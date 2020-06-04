package com.dk.service;

import com.dk.entity.Content;
import com.dk.service.base.IBaseService;

import java.util.List;

public interface ContentServiceI extends IBaseService<Content> {

    Integer getCountByType(Byte i);

    List<Content> getRandomPageByType(Integer countRondom, Integer pageSize, Byte i);
}
