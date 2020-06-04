package com.dk.service;

import com.dk.entity.DomainName;
import com.dk.service.base.IBaseService;

/**
 * @author taotao.yan
 * @version 1.0
 * @create 2019/6/25 14:39
 */
public interface DomainNameService extends IBaseService<DomainName> {

    // 随机获取一个有效的域名
    DomainName getValidDomainNameByRandom();

    // 添加一个域名
    int addDomainName(DomainName domainName);
}
