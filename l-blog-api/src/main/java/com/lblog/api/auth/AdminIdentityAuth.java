package com.lblog.api.auth;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.JwtTokenUtil;
import com.lblog.common.util.RSAUtil;
import com.lblog.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.PublicKey;

public class AdminIdentityAuth {

    @Autowired
    private AdminService adminService;

    public Long getCurrentAdminId(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        String accessToken = httpServletRequest.getHeader("accessToken");
        if(StringUtils.isBlank(accessToken)){
            throw new RuntimeException("accessToken不能为空！");
        }

        Long rsaKeyId = Long.valueOf(httpServletRequest.getHeader("rsaKeyId"));
        if(rsaKeyId == 0){
            throw new ReturnException("rsaKeyId不能为空！");
        }

        //根据accessToken获取userId和公钥字符串
        String publicKeyBase64 = adminService.getAdminRsaKeyById(rsaKeyId).get("publicKeyBase64");
        PublicKey publicKey = RSAUtil.getPublicKey(publicKeyBase64);
        Long adminId = JwtTokenUtil.validateToken(accessToken, publicKey);
        if(adminId < 1){
            throw new ReturnException("非法用户！");
        }

        return adminId;
    }
}
