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

    //accessToken的有效时间（毫秒）
    private static final Long VALID_TIME = 14400000L;

    //refreshToken的有效时间（毫秒）
    private static final Long REFRESH_VALID_TIME = 2592000000L;

    /**
     * 生成accessToken
     * @param userId 用户ID
     * @param privateKey RSA私钥
     * */
    public static String generateAccessToken(Long userId, PrivateKey privateKey){
        Date EXPRIATION = new Date(System.currentTimeMillis() + VALID_TIME);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuer(ISSUER)
                .expiration(EXPRIATION)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    /**
     * 生成refreshToken
     * @param userId 用户ID
     * @param privateKey RSA私钥
     * */
    public static String generateRefreshToken(Long userId, PrivateKey privateKey){
        Date EXPRIATION = new Date(System.currentTimeMillis() + REFRESH_VALID_TIME);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuer(ISSUER)
                .expiration(EXPRIATION)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    /**
     * 校验token
     * @param accessToken 生成的accessToken
     * @param publicKey RSA公钥校验
     * @return userId 用户ID
     * */
    public static Long validateToken(String accessToken, PublicKey publicKey){
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(accessToken)
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
