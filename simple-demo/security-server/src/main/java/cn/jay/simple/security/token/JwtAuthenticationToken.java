package cn.jay.simple.security.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

/**
 * @Author: Jay
 * @Date: 2020/1/8 17:03
 */
@Getter
@Setter
public abstract class JwtAuthenticationToken extends AbstractAuthenticationToken {

    protected String jwtToken = null;

    protected JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    protected void setJwtToken(Long tokenExpireTime, String base64EncodedSecretKey) {
        String jwt = Jwts.builder()
                .claim("Principal", this.getPrincipal())                                  // 用户信息
                .claim("Authorities", this.getAuthorities())                              // 用户权限
                .setSubject(this.getName())                                                     // 主题 - 存用户名
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime))          // 过期时间
                .signWith(SignatureAlgorithm.HS512, base64EncodedSecretKey)                     // 加密算法和密钥
                .compact();
    }

}
