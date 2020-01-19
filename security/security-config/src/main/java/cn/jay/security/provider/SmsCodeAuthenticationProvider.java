package cn.jay.security.provider;

import cn.jay.security.bean.SecurityUser;
import cn.jay.security.token.SmsCodeAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

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
        SecurityUser loadedUser = (SecurityUser) userDetailsService.loadUserByUsername(token.getMobile());
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

    protected Authentication createSuccessAuthentication(SecurityUser loadedUser, Authentication authentication) {
        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(
                loadedUser, authentication.getCredentials(), loadedUser.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsCodeAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
