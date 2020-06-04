package com.dk.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dk.entity.Category;
import com.dk.mapper.CategoryMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.CategoryServiceI;
import com.dk.service.base.impl.BaseServiceImpl;
import com.dk.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wuzu on 2018/4/11.
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryServiceI {
    private Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    protected IBaseDao<Category> getMapper() {
        return categoryMapper;
    }

    @Override
    public List<CategoryVo> selectAll()throws Exception {
        return categoryMapper.selectAll();
    }
}