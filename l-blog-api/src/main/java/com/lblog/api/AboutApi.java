package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.domain.About;
import com.lblog.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutApi {

    @Autowired
    private AboutService aboutService;

    //编辑
    @PostMapping("/editAbout")
    public JsonResponseUtil<String> editAbout(@RequestBody About about){
        aboutService.editAbout(about);

        return JsonResponseUtil.success();
    }

    //获取文章详情
    @GetMapping("/getAboutDetail")
    public JsonResponseUtil<About> getAboutDetail(){
        About aboutDetail = aboutService.getAboutDetail();

        return JsonResponseUtil.success(aboutDetail);
    }
}
