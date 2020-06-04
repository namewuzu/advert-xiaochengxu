package com.dk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * ${DESCRIPTION}.
 * <p>
 * Created by yiqiuhua on 17/3/18.
 */
public class ImgPathUtils
{

    private Logger log = LoggerFactory.getLogger(ImgPathUtils.class);

    private String imgPath;
    private String userImgPath;

    private File file;

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }

    public String getImgPath()
    {
        String imgRealPath = imgPath;
        file = new File(imgRealPath);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return imgRealPath;
    }

    public String getUserImgPath() {
        String userImgRealPath = userImgPath+"/"+DateUtils.getDate(DateUtils.DATE_FORMAT_YYMMDD);
        file = new File(userImgRealPath);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return userImgRealPath;
    }
}
