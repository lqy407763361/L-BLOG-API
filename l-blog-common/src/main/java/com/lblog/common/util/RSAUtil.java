package com.lblog.common.util;
import com.lblog.common.exception.ReturnException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
    //公钥字符串
    public static final String PUBLIC_KEY_BASE64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAohQzWOxW5ZUojrRUyRjD3ZDWUNxYOaX6C/ccpWZu/XsICr6IpFoLlfBimv+onVOKnoBVgXP1c5n6kgJuGzEJDkRER7iVZS89r8zOj1xCPJ853vVEe4KJpVynn+lOIF0MAU/uNiXxh0XdkjLt3jvnupyD5meFnfTO2Cg8ZoKalFVoe3bqDQVBsvDeeRh3f+sKiGeXHhQCYF1/xpBkF4SdELQkR8A0Z7gb7OaICcErwfgYsPe+WKMRR/YQC5aQYfb/TAWjOAOCzvBC9TH39Bg0ZTKI2lPlTCbpXHt55r2YwAo9swNZliTHkNHv2fg6eYk9twdB2a96M36R4HDK3bskvwIDAQAB";

    //私钥字符串
    public static final String PRIVATE_KEY_BASE64 = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCiFDNY7FbllSiOtFTJGMPdkNZQ3Fg5pfoL9xylZm79ewgKvoikWguV8GKa/6idU4qegFWBc/VzmfqSAm4bMQkORERHuJVlLz2vzM6PXEI8nzne9UR7gomlXKef6U4gXQwBT+42JfGHRd2SMu3eO+e6nIPmZ4Wd9M7YKDxmgpqUVWh7duoNBUGy8N55GHd/6wqIZ5ceFAJgXX/GkGQXhJ0QtCRHwDRnuBvs5ogJwSvB+Biw975YoxFH9hALlpBh9v9MBaM4A4LO8EL1Mff0GDRlMojaU+VMJulce3nmvZjACj2zA1mWJMeQ0e/Z+Dp5iT23B0HZr3ozfpHgcMrduyS/AgMBAAECggEAAvO0U7W+nrJjI6BsGizQBeSDbmavKzlC2SmrNp/8/G2hZ688tMjhPvVSDbbU4ERRE0mYiueYfziC6Ley+gzmCd7YbssRTvapH1N6tLoYiI+UZhHm+q3+hCnHq8s2BlZUPAKcfs+dYOjOHwdezKHxl8xTy2cLSIJ7q2mmAuLdtYTMKMp467fls2G5U/NkCxnc7xUUEETIY1g40aeTpVXhJlsWYHetq/cnweswkgNEuYlOi7JqKissVAW9fBHV8olroYuG2wmHorpL9yQDa6gGqSJoWZKWvgdinVdKDPRX2IapIAsAsOuP9irZSsCXpoaO/7ZgG8RnARyN4iuIyRVpQQKBgQC39y56AzvKdY0cHDv+JiWb70XvO1T1p0punPtVTczsOz+q/g38vl8TX3Nc5xEOSjjx//LfC14yAsfadyD+GGPWosOCJ0emj0AC1aYPb/vJ6dduwQsvxIwyQFh8LSqrlKrM9h76Qc7EWNrmqevYOy+hym63UDKRY/SgzXWl8V1p0QKBgQDhixaqD3XyYjUCJlk4H8RpIZI4vCewAVtT/J1RlczqsL/FdkFWRU4YqTS2DLDGnCky9FoQ+RDJI0eLaLJwPj7462r81djiUNJW0mFwj7GykW6D0UsjINN4chn/MYPb3UZdFvE9OcPnyQvbpkQZR1oZ25yEnkeOIqc1UMGn+AW5jwKBgDZyDWtG8bcluzGKmabKG29fDU+UO7zIWhsKksfYUsHHdzxgHDulC/Zr1XS1XjLgfKICohqo/qPGdueNQlJxXXr0iPBIN8hvtP4hxRfkiJxlfMDmmSz1+pgUuxtlMq+9PB+U5+4g89Tgh0ilGYvRWHOFuea+8XheINRGen82Q/NBAoGARPXH+js77t5EIuIiyVw9K3/OO3acE952VBma6EULIH7mQ7PCimQqWrPxn/AFZNR+bEv19daRYU5eI6vhUxxVGLqyPZgM9jWL+mvcyxPEWzrINZmahaUVw2/vru0uydzAFNpc7EFrOsVn0MLnc2szi1BwbMQQUM8bnNuSOo9FANUCgYBBhWFhcAd5h1SXNeajtE8Y1AyZ4NcPcKEaK2LJqm3vkPhsd6hdnXaSUbt79yNXiC6H9RXqWslkbugMBdYI0lFKcW9M424v+C6jgD8KJGHxQQQbMtaTLZ8/rsWnjePLRyuED7tn87/cEbTYABQUBgsLbqkzwQ2XZLmFl77bMtUs4A==";

    //算法名称
    private static final String ALGORITHM = "RSA";

    //密钥长度
    //密钥长度必须大于等于2048位，以适配RS256算法和0.12.X以上版本JWT
    private static final int KEY_SIZE = 2048;

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
            throw new ReturnException("生成密钥对失败！", e);
        }
    }

    /**
     * 公钥加密（默认）
     * @return 解密后的数据
     * */
    public static String encrypt(String data){
        return encrypt(data, PUBLIC_KEY_BASE64);
    }

    /**
     * 公钥加密（重载）
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
            throw new ReturnException("公钥加密失败！", e);
        }
    }

    /**
     * 私钥解密（默认）
     * @param dataBase64 Base64编码加密后的数据
     * @return 解密后的数据
     * */
    public static String decrypt(String dataBase64){
        return decrypt(dataBase64, PRIVATE_KEY_BASE64);
    }

    /**
     * 私钥解密（重载）
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
            throw new ReturnException("私钥解密失败！", e);
        }
    }

    /**
     * 获取公钥对象（默认）
     * @return PublicKey 公钥对象
     * */
    public static PublicKey getPublicKey(){
        return getPublicKey(PUBLIC_KEY_BASE64);
    }

    /**
     * 获取公钥对象（重载）
     * @param publicKeyBase64 公钥Base64字符串
     * @return PublicKey 公钥对象
     * */
    public static PublicKey getPublicKey(String publicKeyBase64){
        try {
            byte[] keyBytes = Base64.decodeBase64(publicKeyBase64);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new ReturnException("公钥转换失败！", e);
        }
    }

    /**
     * 获取私钥对象（默认）
     * @return PrivateKey 私钥对象
     * */
    public static PrivateKey getPrivateKey(){
        return getPrivateKey(PRIVATE_KEY_BASE64);
    }

    /**
     * 获取私钥对象（重载）
     * @param privateKeyBase64 私钥Base64字符串
     * @return PrivateKey 私钥对象
     * */
    public static PrivateKey getPrivateKey(String privateKeyBase64){
        try {
            byte[] keyBytes = Base64.decodeBase64(privateKeyBase64);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new ReturnException("私钥转换失败！", e);
        }
    }

    /**
     * 获取公钥字符串
     * @return PublicKeyBase64 公钥字符串
     * */
    public static String getPublicKeyBase64(){
        return PUBLIC_KEY_BASE64;
    }
}
