package com.xlx.majiang.common.util;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT
 *
 * @author xielx at 2020/4/16 14:45
 */
public class JWTUtil {
    
    private static final Logger log = LoggerFactory.getLogger(JWTUtil.class);
    
    
    public static final long EXPIRATION_TIME = 3 * 60 * 1000L; // 3 min
    
    static final String SECRET = "6KW/57qi5p+/54KS6bih6JuL"; // 西红柿炒鸡蛋
    
    /**
     * 创建jwt
     *
     * @param username     登录用户名
     * @param generateTime jwt创建时间
     * @return jwt字符串
     */
    public static String generateToken(String username, LocalDateTime generateTime) {
        log.info("generateTime={}(LocalDateTime)", generateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        SecretKey secretKey = createSecretKey();
        // Header
        Map<String, Object> header = new HashMap<>(2);
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        
        // PlayLoad的私有字段
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("username", username);
        claims.put("generateTime", generateTime);
        
        // LocalDateTime转Date
        Date date = Date.from(generateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
        
        log.info("generateTime:{}(Date)", date);
        
        String jwt = Jwts.builder()
                             .setHeader(header)
                             .setClaims(claims)
                             .setIssuer("xielx")
                             .setIssuedAt(date)
                             .setExpiration(new Date(date.getTime() + EXPIRATION_TIME))
                             .setId(UUID.randomUUID().toString())
                             .signWith(SignatureAlgorithm.HS256, secretKey)
                             .compact();
        log.info("jwt={}", jwt);
        return jwt;
    }
    
    /**
     * 检验token是否有效,无效会自动抛出异常
     * @param token jwt
     * @return true:有效
     */
    public static Boolean validate(String token) {
        if (token == null) {
            return false;
        }
        boolean after = false;
        try {
            long time = Jwts.parser()
                                .setSigningKey(createSecretKey())
                                .parseClaimsJws(token)
                                .getBody()
                                .getExpiration()
                                .getTime();
    
            LocalDateTime localDateTime = Instant.ofEpochMilli(time).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
            after = LocalDateTime.now().isAfter(localDateTime);
            log.info("token is validated:{},{}",after,(time - System.currentTimeMillis()));
        } catch (SignatureException | MalformedJwtException e) {
            log.error("token无效:{}", e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            log.error("token过期:{}", e.getMessage());
            return false;
        }catch (JwtException jwtEx){
            log.error("token校验异常:{}", jwtEx.getMessage());
            return false;
        }
        
        return true;
    }
    
    /**
     * 获取jwt的payload的Claims部分
     * @param token jwt
     * @return Claims
     */
    public static Claims getJwtClaims(String token){
        return Jwts.parser()
                .setSigningKey(createSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
    
    private static SecretKey createSecretKey() {
        byte[] decode = CryptoUtil.decode(SECRET.getBytes());
        return new SecretKeySpec(decode, 0, decode.length, "AES");
    }
    
}
