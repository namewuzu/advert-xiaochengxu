package com.dk.mapper;

import com.dk.entity.DomainName;
import com.dk.mapper.base.IBaseDao;

/**
 * @author taotao.yan
 * @version 1.0
 * @create 2019/6/25 14:34
 */
public interface DomainNameMapper extends IBaseDao<DomainName> {

    // 随机获取一个有效的域名
    DomainName getValidDomainNameByRandom();

    // 添加一个域名
    int addDomainName(DomainName domainName);
}
