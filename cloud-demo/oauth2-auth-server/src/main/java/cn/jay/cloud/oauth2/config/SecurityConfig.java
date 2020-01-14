package cn.jay.cloud.oauth2.config;

import cn.jay.cloud.oauth2.filter.login.SmsCodeAuthenticationFilter;
import cn.jay.cloud.oauth2.filter.login.UserPwdAuthenticationFilter;
import cn.jay.cloud.oauth2.provider.SmsCodeAuthenticationProvider;
import cn.jay.cloud.oauth2.provider.UserPwdAuthenticationProvider;
import cn.jay.simple.security.bean.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jay
 * @Date: 2020/1/14 10:24
 */
@Slf4j
@Configuration
public class SecurityConfig {

    private final UserDetailsService mobileService;

    private final UserDetailsService usernameService;

    public SecurityConfig(@Qualifier("userPwdService") UserDetailsService usernameService,
                          @Qualifier("smsCodeService") UserDetailsService mobileService) {
        this.mobileService = mobileService;
        this.usernameService = usernameService;
        log.info("=======init SecurityConfig finish==========");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserPwdAuthenticationFilter commonAuthenticationFilter() throws Exception {
        UserPwdAuthenticationFilter filter = new UserPwdAuthenticationFilter();
        setAuthenticationHandler(filter);
        return filter;
    }

    @Bean
    public SmsCodeAuthenticationFilter smsCodeAuthenticationFilter() throws Exception {
        SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
        setAuthenticationHandler(filter);
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(new UserPwdAuthenticationProvider(usernameService, passwordEncoder()));
        providers.add(new SmsCodeAuthenticationProvider(mobileService));
        return new ProviderManager(providers);
    }

    private void setAuthenticationHandler(AbstractAuthenticationProcessingFilter filter) throws Exception {
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            response.getWriter().print("登陆成功:");
        });
        filter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(exception.getMessage());
        });
    }

}
