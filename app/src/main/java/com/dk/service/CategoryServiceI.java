package com.dk.service;

import com.dk.entity.Category;
import com.dk.service.base.IBaseService;
import com.dk.vo.CategoryVo;

import java.util.List;

public interface CategoryServiceI extends IBaseService<Category> {

    List<CategoryVo> selectAll()throws Exception;
}
