package com.lblog.dao;

import com.lblog.domain.SiteConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteConfigDao {
    Integer editSiteConfig(SiteConfig siteConfig);

    SiteConfig getSiteConfigDetail(@Param("id") Long siteConfigId);
}
