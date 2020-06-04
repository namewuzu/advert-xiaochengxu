package com.dk.controller;

import com.dk.entity.DomainName;
import com.dk.entity.base.ResultBody;
import com.dk.service.DomainNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author taotao.yan
 * @version 1.0
 * @create 2019/6/25 14:48
 */
@Controller
@RequestMapping("/dns")
public class DomainNameController {

    @Autowired
    private DomainNameService domainNameService;

    /**
     * 随机获取一个域名
     * @return
     */
    @RequestMapping("/getValidDomainNameByRandom")
    @ResponseBody
    public ResultBody<DomainName> getValidDomainNameByRandom() {
        ResultBody<DomainName> result = new ResultBody("获取一个随机的有效域名成功",200);
        try{
            DomainName domainName = domainNameService.getValidDomainNameByRandom();
            result.setObj(domainName);
        } catch (Exception e) {
            result.setMsgAndStatus("获取一个随机的有效域名失败",500);
        }
        return result;
    }

    /**
     * 添加域名
     * @param domainName
     * @return
     */
    @RequestMapping("/addDomainName")
    @ResponseBody
    public ResultBody<Object> addDomainName(DomainName domainName) {
        ResultBody<Object> result = new ResultBody("添加域名成功",200);
        try{
            int effectRows = domainNameService.addDomainName(domainName);
            result.setMsg("本次添加了" + effectRows +"个域名");
        } catch (Exception e) {
            result.setMsgAndStatus("添加域名失败",500);
        }
        return result;
    }

}
