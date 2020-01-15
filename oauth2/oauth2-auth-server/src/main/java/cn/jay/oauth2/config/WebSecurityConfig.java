package cn.jay.oauth2.config;

import cn.jay.oauth2.ConfigProperties;
import cn.jay.oauth2.filter.login.SmsCodeAuthenticationFilter;
import cn.jay.oauth2.filter.login.UserPwdAuthenticationFilter;
import cn.jay.oauth2.filter.token.JwtAuthenticationTokenFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Jay
 * @Date: 2020/1/14 9:19
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    private final ConfigProperties configProperties;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private final UserPwdAuthenticationFilter userPwdAuthenticationFilter;

    private final SmsCodeAuthenticationFilter smsCodeAuthenticationFilter;

    public WebSecurityConfig(ConfigProperties configProperties,
                             UserPwdAuthenticationFilter userPwdAuthenticationFilter,
                             SmsCodeAuthenticationFilter smsCodeAuthenticationFilter,
                             JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.userPwdAuthenticationFilter = userPwdAuthenticationFilter;
        this.smsCodeAuthenticationFilter = smsCodeAuthenticationFilter;
        this.configProperties = configProperties;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        log.info("=======init WebSecurityConfig finish==========");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.antMatcher("/**").authorizeRequests();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(2)                 //最大session数量2
                .maxSessionsPreventsLogin(true)
//            .and().
        ;    //最大session时阻止登陆
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

        http.addFilterAt(userPwdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
