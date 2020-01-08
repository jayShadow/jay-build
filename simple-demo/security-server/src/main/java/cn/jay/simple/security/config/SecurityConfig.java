package cn.jay.simple.security.config;

import cn.jay.simple.security.ConfigProperties;
import cn.jay.simple.security.filter.login.UserPwdAuthenticationFilter;
import cn.jay.simple.security.filter.login.SmsCodeAuthenticationFilter;
import cn.jay.simple.security.filter.token.JwtAuthenticationTokenFilter;
import cn.jay.simple.security.provider.UserPwdAuthenticationProvider;
import cn.jay.simple.security.provider.SmsCodeAuthenticationProvider;
import cn.jay.simple.security.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Jay
 * @Date: 2019/12/24 15:57
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenUtil jwtTokenUtil;

    private final ObjectMapper objectMapper;

    private final UserDetailsService mobileService;

    private final UserDetailsService usernameService;

    private final ConfigProperties configProperties;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    public SecurityConfig(JwtTokenUtil jwtTokenUtil, ConfigProperties configProperties,
                          @Qualifier("userPwdServiceImpl") UserDetailsService usernameService,
                          @Qualifier("smsCodeServiceImpl") UserDetailsService mobileService,
                          JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, ObjectMapper objectMapper) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.objectMapper = objectMapper;
        this.mobileService = mobileService;
        this.usernameService = usernameService;
        this.configProperties = configProperties;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

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

    private void setAuthenticationHandler(AbstractAuthenticationProcessingFilter filter) throws Exception {
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            response.getWriter().print("登陆成功:"+ jwtTokenUtil.generateToken(userDetails));
        });
        filter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(exception.getMessage());
        });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        AuthenticationProvider commonAuthenticationProvider =
                new UserPwdAuthenticationProvider(usernameService, passwordEncoder());
        AuthenticationProvider smsCodeAuthenticationProvider =
                new SmsCodeAuthenticationProvider(mobileService);
        auth.authenticationProvider(commonAuthenticationProvider)
                .authenticationProvider(smsCodeAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.antMatcher("/**").authorizeRequests();
        // 禁用sessionAdminAuthenticationEntryPoint
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用CSRF 开启跨域
        http.csrf().disable().cors();

        for (String url : configProperties.getAuth().getIgnoreUrls()) {
            registry.antMatchers(url).permitAll();
        }
        // 剩余地址鉴权(必须在ignore url之后)
        registry.anyRequest().authenticated();

        // 未登录认证异常
        http.exceptionHandling().authenticationEntryPoint((request, response, exception) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(objectMapper.writeValueAsString(exception.getMessage()));
        });
        // 登录过后访问无权限的接口时自定义403响应内容
        http.exceptionHandling().accessDeniedHandler((request, response, exception) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(objectMapper.writeValueAsString(exception.getMessage()));
        });

        http.addFilterAt(commonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(smsCodeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationTokenFilter, BasicAuthenticationFilter.class);
    }

}
