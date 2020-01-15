package cn.jay.cloud.oauth2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @Author: Jay
 * @Date: 2020/1/13 16:28
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    public AuthorizationServerConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        log.info("=======init AuthorizationServerConfig finish==========");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");
        security.allowFormAuthenticationForClients(); // 允许url添加client_id及client_secret进行客户端验证
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("identity").resourceIds("resource-identity")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("password", "authorization_code", "implicit")
                .scopes("all","demo")
                .redirectUris("http://localhost:8080")
                .autoApprove(false)
                .and()
                .withClient("client_book")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("client_credentials")
                .scopes("all")
                .redirectUris("http://localhost:8080")
                .autoApprove(false);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
}
