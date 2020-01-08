package cn.jay.simple.security.utils;

import cn.jay.simple.security.ConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jay
 * @Date: 2020/1/3 9:55
 */
@Component
public class JwtTokenUtil {

    private final String secret;

    private final Long tokenExpireTime;

    private final Long rememberMeExpireTime;

    private JwtTokenUtil(ConfigProperties configProperties) {
        this.secret = configProperties.getAuth().getSecret();
        this.tokenExpireTime = configProperties.getAuth().getTokenExpireTime();
        this.rememberMeExpireTime = configProperties.getAuth().getRememberMeExpireTime();
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private final String generateToken(String sub, Map<String, Object> claims, Boolean rememberMe) {
        Long expireTime = rememberMe ? rememberMeExpireTime : tokenExpireTime;
        return Jwts.builder()
                .setSubject(sub)
                .setClaims(claims)// 主题 - 存用户名
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))      // 过期时间
                .signWith(SignatureAlgorithm.HS512, secret)                                 // 加密算法和密钥
                .compact();
    }

    private final String generateToken(String sub, Map<String, Object> claims) {
        return generateToken(sub, claims, false);
    }

    private final String generateToken(Map<String, Object> claims) {
        return generateToken(null, claims);
    }

    /**
     * 生成令牌
     *
     * @param userDetails 用户
     * @return 令牌
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Claims.SUBJECT, userDetails.getUsername());
        claims.put(Claims.ISSUED_AT, new Date());
        return generateToken(claims);
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
     * 从令牌中获取用户名
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
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(Claims.ISSUED_AT, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
//        JwtUser user = (JwtUser) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
