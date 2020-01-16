package cn.jay.oauth2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * @Author: Jay
 * @Date: 2020/1/13 16:28
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final ClientDetailsService clientDetailsService;

    private final AuthorizationServerTokenServices tokenServices;

    private final AuthenticationManager authenticationManager;

    public AuthorizationServerConfig(@Qualifier("clientService") ClientDetailsService clientDetailsService,
                                     AuthorizationServerTokenServices tokenServices,
                                     AuthenticationManager authenticationManager) {
        this.tokenServices = tokenServices;
        this.clientDetailsService = clientDetailsService;
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
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenServices(tokenServices);
        endpoints.authenticationManager(authenticationManager);
    }
}
