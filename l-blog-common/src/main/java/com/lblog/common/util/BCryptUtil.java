package com.lblog.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUtil {
    /**
     * 加密密码
     * @param rawPassword 原始密码
     * @param strength 加密强度（范围4-31，默认为10，强度越高越安全，但运行效率越慢）
     * @return 返回加密后的密码
     * */
    public static String getEncrypt(String rawPassword, int strength){
        if((strength < 4) || (strength > 31)){
            strength = 10;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength);

        return encoder.encode(rawPassword);
    }

    /**
     * 验证密码，验证时无需强度参数！
     * @param rawPassword 原始密码
     * @param encodePassword 加密后的密码
     * @return 返回密码是否匹配
     * */
    public static Boolean validateCode(String rawPassword, String encodePassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(rawPassword, encodePassword);
    }
}
