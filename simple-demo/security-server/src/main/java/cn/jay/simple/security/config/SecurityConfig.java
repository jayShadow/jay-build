package cn.jay.simple.security.config;

import cn.jay.simple.security.ConfigProperties;
import cn.jay.simple.security.filter.CommonAuthenticationFilter;
import cn.jay.simple.security.filter.JwtAuthenticationTokenFilter;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private ObjectMapper objectMapper;

    private ConfigProperties configProperties;

    private UserDetailsService userDetailsService;

    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    public SecurityConfig(ConfigProperties configProperties,
                          JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, ObjectMapper objectMapper,
                          UserDetailsService userDetailsService) {
        this.objectMapper = objectMapper;
        this.configProperties = configProperties;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CommonAuthenticationFilter commonAuthenticationFilter() throws Exception {
        CommonAuthenticationFilter filter = new CommonAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().print(JSONObject.toJSONString(authentication));
        });
        filter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(exception.getMessage());
        });
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        auth.authenticationProvider(daoProvider);
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
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(objectMapper.writeValueAsString(exception.getMessage()));
        });
        // 登录过后访问无权限的接口时自定义403响应内容
        http.exceptionHandling().accessDeniedHandler((request, response, exception) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(objectMapper.writeValueAsString(exception.getMessage()));
        });

        http.addFilterAt(commonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationTokenFilter, BasicAuthenticationFilter.class);
    }

}
