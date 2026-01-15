package com.lblog.api.auth;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.JwtTokenUtil;
import com.lblog.common.util.RSAUtil;
import com.lblog.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.PublicKey;

@Component
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

        //根据accessToken和RSA公钥获取adminId
        PublicKey publicKey = RSAUtil.getPublicKey();
        Long adminId = JwtTokenUtil.validateToken(accessToken, publicKey);
        if(adminId < 1){
            throw new ReturnException("非法用户！");
        }

        return adminId;
    }
}
