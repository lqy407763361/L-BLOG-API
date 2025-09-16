package com.lblog.common.util;

import com.lblog.common.exception.ReturnException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

public class JwtTokenUtil {
    //签发人
    private static final String ISSUER = "L-BLOG";

    //有效时间（秒）
    private static final long VALID_TIME = 86400;

    /**
     * 生成token
     * @param userId 用户ID
     * @param privateKey RSA私钥签名
     * */
    public static String generateToken(Long userId, PrivateKey privateKey){
        Date EXPRIATION = new Date(System.currentTimeMillis() + VALID_TIME);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuer(ISSUER)
                .expiration(EXPRIATION)
                .signWith(privateKey)
                .compact();
    }

    //校验token
    /**
     * 校验token
     * @param token 生成的token
     * @param publicKey RSA公钥校验
     * @return userId 用户ID
     * */
    public static Long validateToken(String token, PublicKey publicKey){
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Long userId = Long.valueOf(claims.getSubject());

            return userId;
        } catch (ExpiredJwtException e) {
            throw new ReturnException("token过期！", e);
        } catch (Exception e) {
            throw new ReturnException("校验失败！", e);
        }
    }
}
