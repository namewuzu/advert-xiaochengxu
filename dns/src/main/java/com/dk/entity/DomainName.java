package com.dk.entity;

/**
 * 域名实体类，用于映射t_domain_name_valid表
 * @author taotao.yan
 * @version 1.0
 * @create 2019/6/25 14:36
 */
public class DomainName {

    private Integer id;
    private String domainName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
