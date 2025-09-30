package com.lblog.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class GetClientIpUtil {
    //获取用户IP地址
    public static String getClientIp(HttpServletRequest request){
        String ip = null;
        ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }

        if(ip != null && ip.contains(",")){
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
}