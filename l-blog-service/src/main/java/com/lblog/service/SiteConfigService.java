package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.dao.SiteConfigDao;
import com.lblog.domain.SiteConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class SiteConfigService {
    private static final Long SITE_CONFIG_ID = 1L;

    @Autowired
    private SiteConfigDao siteConfigDao;

    @Transactional
    public void editSiteConfig(SiteConfig siteConfig){
        //更改配置信息
        String metaTitle = "";
        if(!StringUtils.isBlank(siteConfig.getMetaTitle())){
            metaTitle = siteConfig.getMetaTitle().trim();
        }

        String metaDescription = "";
        if(!StringUtils.isBlank(siteConfig.getMetaDescription())){
            metaDescription = siteConfig.getMetaDescription().trim();
        }

        String metaKeywords = "";
        if(!StringUtils.isBlank(siteConfig.getMetaKeywords())){
            metaKeywords = siteConfig.getMetaKeywords().trim();
        }

        String siteTitle = "";
        if(!StringUtils.isBlank(siteConfig.getSiteTitle())){
            siteTitle = siteConfig.getSiteTitle().trim();
        }

        String logoImageUrl = "";
        if(!StringUtils.isBlank(siteConfig.getLogoImageUrl())){
            logoImageUrl = siteConfig.getLogoImageUrl().trim();
        }

        String siteConfigStr = "";
        if(!StringUtils.isBlank(siteConfig.getSiteConfig())){
            siteConfigStr = siteConfig.getSiteConfig().trim();
        }

        if(StringUtils.isBlank(metaTitle) || StringUtils.isBlank(metaDescription) || StringUtils.isBlank(metaKeywords) || StringUtils.isBlank(siteTitle)){
            throw new ReturnException("基础信息不能为空！");
        }

        Integer siteListLimit = siteConfig.getSiteListLimit();
        Integer adminListLimit = siteConfig.getAdminListLimit();
        if((siteListLimit == null) || (siteListLimit == 0) || (adminListLimit == null) || (adminListLimit == 0)){
            throw new ReturnException("分页数量不能为0！");
        }

        Integer systemMaintenance = siteConfig.getSystemMaintenance();
        Integer siteLoginMaxNumber = siteConfig.getSiteLoginMaxNumber();
        Integer adminLoginMaxNumber = siteConfig.getAdminLoginMaxNumber();
        if((siteLoginMaxNumber == null) || (siteLoginMaxNumber == 0) || (adminLoginMaxNumber == null) || (adminLoginMaxNumber == 0)){
            throw new ReturnException("最小登录次数不能为0！");
        }

        Long siteSessionExpire = siteConfig.getSiteSessionExpire();
        Long adminSessionExpire = siteConfig.getAdminSessionExpire();
        if((siteSessionExpire == null) || (siteSessionExpire == 0) || (adminSessionExpire == null) || (adminSessionExpire == 0)){
            throw new ReturnException("会话有效期不能为0！");
        }

        Long editTime = Instant.now().getEpochSecond();
        siteConfig.setId(SITE_CONFIG_ID);
        siteConfig.setMetaTitle(metaTitle);
        siteConfig.setMetaDescription(metaDescription);
        siteConfig.setMetaKeywords(metaKeywords);;
        siteConfig.setSiteTitle(siteTitle);
        siteConfig.setSiteListLimit(siteListLimit);
        siteConfig.setAdminListLimit(adminListLimit);
        siteConfig.setLogoImageUrl(logoImageUrl);
        siteConfig.setSiteConfig(siteConfigStr);
        siteConfig.setSystemMaintenance(systemMaintenance);
        siteConfig.setSiteLoginMaxNumber(siteLoginMaxNumber);
        siteConfig.setAdminLoginMaxNumber(adminLoginMaxNumber);
        siteConfig.setSiteSessionExpire(siteSessionExpire);
        siteConfig.setAdminSessionExpire(adminSessionExpire);
        siteConfig.setEditTime(editTime);
        Integer returnRow = siteConfigDao.editSiteConfig(siteConfig);
        if((returnRow == null) || (returnRow == 0)){
            throw new ReturnException("编辑失败！");
        }
    }

    //获取配置详情
    public SiteConfig getSiteConfigDetail(){

        return siteConfigDao.getSiteConfigDetail(SITE_CONFIG_ID);
    }
}
