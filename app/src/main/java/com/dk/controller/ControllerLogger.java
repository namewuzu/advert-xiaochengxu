package com.dk.controller;

import com.dk.util.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class ControllerLogger
{

    public void before(JoinPoint jp)
    {
        jp.getArgs();// 此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
        System.out.println(DateUtils.getNowTime()+"被拦截方法调用之前调用此方法，输出此语句" + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }

    public void after(JoinPoint jp)
    {
        System.out.println(DateUtils.getNowTime()+"被拦截方法调用之后调用此方法，输出此语句.." + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }

    public Object around(ProceedingJoinPoint pjp) throws Throwable
    {
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        System.out.println(pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName() + " process time: " + time + " ms");
        return retVal;
    }

    public void throwing(JoinPoint jp, Throwable ex)
    {
        System.out.println(DateUtils.getNowTime()+"method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " throw exception");
        System.out.println(ex.getMessage());
    }
}
