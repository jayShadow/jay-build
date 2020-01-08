package cn.jay.simple.security.provider;

import cn.jay.simple.security.token.SmsCodeAuthenticationToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.Duration;
import java.util.Date;

/**
 * @Author: Jay
 * @Date: 2020/1/8 10:53
 */
@Slf4j
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public SmsCodeAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken token = (SmsCodeAuthenticationToken) authentication;
        UserDetails loadedUser = userDetailsService.loadUserByUsername(token.getMobile());
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        if (authentication.getCredentials() == null) {
            log.debug("Authentication failed: 验证码不能为空");
            throw new BadCredentialsException("验证码不能为空");
        }
        String smscode = authentication.getCredentials().toString();
        if (!StringUtils.equals(smscode, loadedUser.getUsername())) {
            log.debug("Authentication failed: 验证码错误");
            throw new BadCredentialsException("验证码错误");
        }
        return createSuccessAuthentication(loadedUser, authentication);
    }

    protected Authentication createSuccessAuthentication(UserDetails principal, Authentication authentication) {
        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(
                principal, authentication.getCredentials(), principal.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsCodeAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
