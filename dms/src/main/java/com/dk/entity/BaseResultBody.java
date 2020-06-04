package com.dk.entity;

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
    protected String status;

    public BaseResultBody()
    {
    }

    /**
     * @author yiqh
     * @desc
     * @param msg
     * @param status
     */
    public BaseResultBody(String msg, String status)
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
