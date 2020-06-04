package com.dk.util;

import java.util.UUID;

/**
 * Created by wuzu on 2018/4/4.
 */
public class UuidUtils {
    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
