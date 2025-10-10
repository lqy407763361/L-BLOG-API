package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.domain.SiteConfig;
import com.lblog.service.SiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteConfigApi {

    @Autowired
    private SiteConfigService siteConfigService;

    //编辑
    @PostMapping("editSiteConfig")
    public JsonResponseUtil<String> editSiteConfig(SiteConfig siteConfig){
        siteConfigService.editSiteConfig(siteConfig);

        return JsonResponseUtil.success();
    }

    //获取配置详情
    @GetMapping("/getSiteConfigDetail")
    public JsonResponseUtil<SiteConfig> getSiteConfigDetail(){
        SiteConfig siteConfig = siteConfigService.getSiteConfigDetail();

        return JsonResponseUtil.success(siteConfig);
    }
}
