package com.dk.service;

import com.dk.entity.ShareWeight;
import com.dk.service.base.IBaseService;

import java.util.List;

public interface ShareWeightServiceI extends IBaseService<ShareWeight>{

    List<ShareWeight> selectAll();
}
