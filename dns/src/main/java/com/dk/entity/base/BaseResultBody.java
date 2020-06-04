package com.dk.entity.base;

/**
 * ${DESCRIPTION}.
 * <p>
 * Created by yiqiuhua on 17/3/26.
 */
public class BaseResultBody
{

    //返回信息
    protected String msg;
    //返回状态 000异常，001有数据体，002没有数据体
    protected Integer status;

    public BaseResultBody()
    {
    }

    /**
     * @author yiqh
     * @desc
     * @param msg
     * @param status
     */
    public BaseResultBody(String msg, Integer status)
    {
        this.msg = msg;
        this.status = status;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
