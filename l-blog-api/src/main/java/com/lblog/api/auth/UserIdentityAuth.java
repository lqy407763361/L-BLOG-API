package com.lblog.api.auth;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.JwtTokenUtil;
import com.lblog.common.util.RSAUtil;
import com.lblog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.PublicKey;

@Component
public class UserIdentityAuth {

    @Autowired
    private UserService userService;

    public Long getCurrentUserId(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        String accessToken = httpServletRequest.getHeader("accessToken");
        if(StringUtils.isBlank(accessToken)){
            throw new RuntimeException("accessToken不能为空！");
        }

        //根据accessToken和RSA公钥获取userId
        PublicKey publicKey = RSAUtil.getPublicKey();
        Long userId = JwtTokenUtil.validateToken(accessToken, publicKey);
        if(userId < 1){
            throw new ReturnException("非法用户！");
        }

        return userId;
    }
}
