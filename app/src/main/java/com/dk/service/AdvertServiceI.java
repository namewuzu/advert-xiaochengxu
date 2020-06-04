package com.dk.service;

import com.dk.entity.Advert;
import com.dk.service.base.IBaseService;

import java.util.List;

public interface AdvertServiceI extends IBaseService<Advert>{

    List<Advert> selectAll();

    List<Advert> selectByAdvertType(Byte advertType);
}
