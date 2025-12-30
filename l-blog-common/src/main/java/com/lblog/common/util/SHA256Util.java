package com.lblog.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;

public class SHA256Util {
    /**
     * 生成随机数，随机数池可自定义
     * @param randomLen 自定义需要返回随机字符串的长度
     * @return 返回随机字符串
    * */
    public static String RandomString(int randomLen){
        if(randomLen == 0){
            randomLen = 8;
        }
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String randomString = RandomStringUtils.random(randomLen, str);

        return randomString;
    }

    /**
     * 加盐后SHA256加密
     * @param str 需要加密的字符串
     * @param salt 盐值
     * @return 返回加密后的字符串
    * */
    public static String getEncrypt(String str, String salt){
        if(StringUtils.isBlank(salt)){
            salt = String.valueOf(Instant.now().getEpochSecond()) + RandomString(8);
        }
        String sha256Str = DigestUtils.sha256Hex(str + salt);

        return sha256Str;
    }
}
