package cn.jay.simple.security.config;

import cn.jay.simple.security.ConfigProperties;
import cn.jay.simple.security.filter.AuthenticationFilter;
import cn.jay.simple.security.filter.JwtAuthenticationTokenFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    private AuthenticationFilter authenticationFilter;

    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    public SecurityConfig(ConfigProperties configProperties, AuthenticationFilter authenticationFilter, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.configProperties = configProperties;
        this.authenticationFilter = authenticationFilter;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
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

        http.addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationTokenFilter, BasicAuthenticationFilter.class);
    }

}
