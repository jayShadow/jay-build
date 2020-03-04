package cn.jay.security.provider;

import cn.jay.security.entity.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: Jay
 * @Date: 2020/1/8 10:03
 */
@Slf4j
public class UserPwdAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public UserPwdAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        SecurityUser loadedUser = (SecurityUser) userDetailsService.loadUserByUsername(token.getName());
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        if (authentication.getCredentials() == null) {
            log.debug("Authentication failed: 密码不能为空");
            throw new BadCredentialsException("密码不能为空");
        }
        String password = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(password, loadedUser.getPassword())) {
            log.debug("Authentication failed: 密码错误");
            throw new BadCredentialsException("密码错误");
        }
        return createSuccessAuthentication(loadedUser, authentication);
    }

    protected Authentication createSuccessAuthentication(SecurityUser loadedUser, Authentication authentication) {
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                loadedUser, authentication.getCredentials(), loadedUser.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
