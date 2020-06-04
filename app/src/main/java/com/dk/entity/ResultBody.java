package com.dk.entity;

import java.io.Serializable;

/**
 * ${DESCRIPTION}.
 * <p>
 * Created by yiqiuhua on 17/3/14.
 */
public class ResultBody<T> extends BaseResultBody implements Serializable
{

    private static final long serialVersionUID = -8626495836492662629L;

    //返回数据
    private T obj;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    /**
     * @author yiqh
     * @desc 空参构造函数
     */

    public ResultBody()
    {
    }


    /**
     * 返回状态和返回信息的构造函数
     * @param msg
     * @param status
     */
    public ResultBody(String msg, int status)
    {
        super(msg,status);
    }


    /**
     * 设置异常返回
     * @param msg
     */
    public ResultBody(String  msg)
    {
        this.msg = msg;
        this.status = 200;
    }


    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public void setMsgAndStatus(String msg, int status) {
        setMsg(msg);
        setStatus(status);
    }
}
