package com.lblog.common.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
    private static final String ALGORITHM = "RSA";

    private static final int KEY_SIZE = 1024;

    public static void main(String[] args) throws Exception{
    }

    //获取密钥对
    public static Map<String, String> getKeyPair(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);

            Map<String, String> keyMap = new HashMap<>();
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("生成密钥对失败！", e);
        }

    }

    //加密
    public static String encrypt(){

    }

    //解密
    public static String decrypt(){

    }
}
