package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.domain.SiteConfig;
import com.lblog.service.SiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SiteConfigApi {

    @Autowired
    private SiteConfigService siteConfigService;

    //编辑
    @PostMapping("/editSiteConfig")
    public JsonResponseUtil<String> editSiteConfig(@RequestBody SiteConfig siteConfig){
        siteConfigService.editSiteConfig(siteConfig);

        return JsonResponseUtil.success();
    }

    //上传LOGO图片
    @PostMapping("/uploadLogoImage")
    public JsonResponseUtil<String> uploadLogoImage(@RequestParam("logoImage") MultipartFile logoImage){
        siteConfigService.uploadLogoImage(logoImage);

        return JsonResponseUtil.success();
    }

    //获取配置详情
    @GetMapping("/getSiteConfigDetail")
    public JsonResponseUtil<SiteConfig> getSiteConfigDetail(){
        SiteConfig siteConfig = siteConfigService.getSiteConfigDetail();

        return JsonResponseUtil.success(siteConfig);
    }
}
