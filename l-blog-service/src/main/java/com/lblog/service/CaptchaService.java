package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.CaptchaUtil;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class CaptchaService {
    private static final String CAPTCHA_SESSION_KEY = "captcha";

    //生成验证码图片，并将code保存到session
    @Transactional
    public String generateCaptcha(HttpSession session){
        String code = CaptchaUtil.generateCode();
        BufferedImage captchaImage = CaptchaUtil.generateImage(code);

        //保存到session
        session.setAttribute(CAPTCHA_SESSION_KEY, code);

        try{
            String base64Image = CaptchaUtil.imageToBase64(captchaImage);
            return base64Image;
        } catch (IOException e) {
            throw new ReturnException("验证码生成失败！");
        }
    }

    //验证
    @Transactional
    public Boolean validateCaptcha(HttpSession session, String inputCode){
        if(StringUtils.isBlank(inputCode)){
            throw new ReturnException("验证码不能为空！");
        }

        //从session获取验证码
        String sessionCode = (String) session.getAttribute(CAPTCHA_SESSION_KEY);
        if(StringUtils.isBlank(sessionCode)){
            throw new ReturnException("SESSION不存在验证码！");
        }

        return CaptchaUtil.validateCode(inputCode, sessionCode);
    }
}
