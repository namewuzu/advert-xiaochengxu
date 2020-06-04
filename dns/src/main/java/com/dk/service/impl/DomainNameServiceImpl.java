package com.dk.service.impl;

import com.dk.entity.DomainName;
import com.dk.mapper.DomainNameMapper;
import com.dk.mapper.base.IBaseDao;
import com.dk.service.DomainNameService;
import com.dk.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author taotao.yan
 * @version 1.0
 * @create 2019/6/25 14:41
 */
@Service
public class DomainNameServiceImpl extends BaseServiceImpl<DomainName> implements DomainNameService {

    @Autowired
    private DomainNameMapper domainNameMapper;

    @Override
    protected IBaseDao<DomainName> getMapper() {
        return domainNameMapper;
    }

    // 随机获取一个有效的域名
    @Override
    public DomainName getValidDomainNameByRandom() {
        return domainNameMapper.getValidDomainNameByRandom();
    }

    // 添加一个域名
    @Override
    public int addDomainName(DomainName domainName) {
        return domainNameMapper.addDomainName(domainName);
    }
}
