package com.lblog.common.util;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
    //算法名称
    private static final String ALGORITHM = "RSA";

    //密钥长度
    private static final int KEY_SIZE = 1024;

    //字符串编码类型
    private static final String UNICODE = "UTF-8";

    //获取密钥对
    public static Map<String, String> getKeyPair(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            String publicKeyBase64 = Base64.encodeBase64String(publicKey.getEncoded());
            String privateKeyBase64 = Base64.encodeBase64String(privateKey.getEncoded());

            Map<String, String> keyMap = new HashMap<>();
            keyMap.put("publicKeyBase64", publicKeyBase64);
            keyMap.put("privateKeyBase64", privateKeyBase64);

            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("生成密钥对失败！", e);
        }
    }

    /**
     * 公钥加密
     * @param data 待加密数据
     * @param publicKeyBase64 Base64编码的公钥
     * @return 加密后返回的Base64字符串
     * */
    public static String encrypt(String data, String publicKeyBase64){
        try {
            byte[] publicKeyBytes = Base64.decodeBase64(publicKeyBase64);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(UNICODE));

            return Base64.encodeBase64String(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 私钥解密
     * @param dataBase64 Base64编码加密后的数据
     * @param privateKeyBase64 Base64编码的私钥
     * @return 解密后的数据
    * */
    public static String decrypt(String dataBase64, String privateKeyBase64){
        try {
            byte[] privateKeyBytes = Base64.decodeBase64(privateKeyBase64);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] encryptData = Base64.decodeBase64(dataBase64);
            byte[] data = cipher.doFinal(encryptData);

            return new String(data, UNICODE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
