package cn.jay.security.utils;

import cn.jay.security.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jay
 * @Date: 2020/1/3 9:55
 */
@Slf4j
@Component
public class JwtTokenUtil {

    private final String secret;

    private final Long tokenExpireTime;

    private final Long rememberMeExpireTime;

    public JwtTokenUtil(@Value("${security.jwt.secret}") String secret,
                        @Value("${security.jwt.tokenExpireMinute}") Long tokenExpireMinute,
                        @Value("${security.jwt.rememberMeExpireDay}") Integer rememberMeExpireDay) {
        this.secret = secret;
        this.tokenExpireTime = tokenExpireMinute * 60 * 1000;
        this.rememberMeExpireTime = rememberMeExpireDay * 24 * 3600 * 1000L;
        log.info("JwtTokenUtil init success");
    }

    /**
     * 从数据声明生成令牌
     *
     * @param sub        主题
     * @param claims     数据声明
     * @param rememberMe 是否记住
     * @return 令牌
     */
    public final String generateToken(String sub, Map<String, Object> claims, Boolean rememberMe) {
        Long expireTime = rememberMe ? rememberMeExpireTime : tokenExpireTime;
        return Jwts.builder()
                .setClaims(claims)      // 必须先设置 否则将覆盖
                .setSubject(sub)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))      // 过期时间
                .signWith(SignatureAlgorithm.HS512, secret)                                 // 加密算法和密钥
                .compact();
    }

    /**
     * 从数据声明生成令牌
     *
     * @param sub 主题
     * @return 令牌
     */
    public final String generateToken(String sub) {
        return generateToken(sub, new HashMap<>(), false);
    }

    /**
     * 生成令牌
     *
     * @param loginUser 用户
     * @return 令牌
     */
    public final String generateToken(LoginUser loginUser) {
        return generateToken(loginUser.getUsername(), new HashMap<>(), false);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    /**
     * 从令牌中获取主题
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            String sub = getUsernameFromToken(token);
            return generateToken(sub, claims, false);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
