package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.service.CaptchaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaApi {

    @Autowired
    private CaptchaService captchaService;

    //获取验证码
    @GetMapping("/generateCaptcha")
    public JsonResponseUtil<String> generateCaptcha(HttpSession session){
        String imageBase64 = captchaService.generateCaptcha(session);

        return JsonResponseUtil.success(imageBase64);
    }

    //验证
    @PostMapping("/validateCaptcha")
    public JsonResponseUtil<Boolean> validateCaptcha(HttpSession session, String code){
        Boolean isValid = captchaService.validateCaptcha(session, code);

        return JsonResponseUtil.success(isValid);
    }
}
