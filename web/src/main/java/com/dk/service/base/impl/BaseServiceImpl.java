package com.dk.service.base.impl;

import com.dk.mapper.base.IBaseDao;
import com.dk.service.base.IBaseService;
import com.dk.util.PageResult;
import com.github.pagehelper.PageHelper;
import org.apache.log4j.Logger;

import java.util.List;


/**
 * 通用Service接口定义了新增、修改、删除、查询单个记录、查询记录列表、分页查询列表的方法
 *
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {

    private static Logger logger = Logger.getLogger(BaseServiceImpl.class);

    protected abstract IBaseDao<T> getMapper();

    @Override
    public int insertSelective(T entity) throws Exception {
        return getMapper().insertSelective(entity);
    }
    /**
     * 插入数据(选择性地)
     *
     * @param entity
     * @return
     * @throws Exception
     * @throws
     */
    @Override
    public int updateByPrimaryKeySelective(T entity) throws Exception {
        return getMapper().updateByPrimaryKeySelective(entity);
    }
    @Override
    public int deleteByPrimaryKey(int id)throws Exception{
        return getMapper().deleteByPrimaryKey(id);
    }
    @Override
    public int insert(T entity)  {
        int num=0;
        try {
            num=getMapper().insertSelective(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return num;
    }
    @Override
    public int update(T entity) {
        int num=0;
        try {
            num=getMapper().updateByPrimaryKeySelective(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return num;
    }

    @Override
    public int delete(int id){
        int num=0;
        try {
            num=getMapper().deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return num;
    }
    @Override
    public T selectByPrimaryKey(int id) {
        T obj = null;
        try {
            obj = getMapper().selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return obj;
    }

    @Override
    public T selectByPrimaryKeyString(String id) {
        T obj = null;
        try {
            obj = getMapper().selectByPrimaryKeyString(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return obj;
    }
    @Override
    public int getCount(T entity) {
        int result = 0;
        try {
            result = getMapper().getCount(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    public List<T> getAll(){
        List<T> list = null;
        try {
            list = getMapper().getAll();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return list;
    }
    public List<T> getAllBySelect(T entity){
        List<T> list = null;
        try {
            list = getMapper().getAllBySelect(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public List<T> getPage(T obj){
        List<T> list = null;
        try {
            list = getMapper().getPage(obj);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return list;
    }
    @Override
    public PageResult<T> queryByPage(PageResult<T> t, T entity) {
        int pageNo=t.getPageNo();
        int pageSize=t.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getPage(entity),t);
    }


    @Override
    public List<T> getPageFront(T obj){
        List<T> list = null;
        try {
            list = getMapper().getPageFront(obj);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public PageResult<T> queryByPageFront(PageResult<T> t,T entity) {
        int pageNo=t.getPageNo();
        int pageSize=t.getPageSize();
        pageNo = pageNo == 0?1:pageNo;
        pageSize = pageSize == 0?10:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        return PageResult.toPageResult(getPageFront(entity),t);
    }
    
}
