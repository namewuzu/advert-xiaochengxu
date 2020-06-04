package com.dk.service;

import com.dk.entity.Category;
import com.dk.service.base.IBaseService;

import java.util.List;

public interface CategoryServiceI extends IBaseService<Category>{

    List<Category> selectAll();
}
