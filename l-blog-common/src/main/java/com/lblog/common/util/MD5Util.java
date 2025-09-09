package com.lblog.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;

public class MD5Util {
    //生成随机数，随机数池可自定义
    private static String RandomString(int randomLen){
        if(randomLen == 0){
            randomLen = 8;
        }
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String randomString = RandomStringUtils.random(randomLen, str);

        return randomString;
    }

    //加盐后MD5加密
    public static String getEncrypt(String str){
        String salt = String.valueOf(Instant.now().toEpochMilli()) + RandomString(8);
        String md5Str = DigestUtils.md5Hex(str+salt);
        StringUtils.substring(md5Str, 0, md5Str.length()-1);

        return md5Str;
    }
}
