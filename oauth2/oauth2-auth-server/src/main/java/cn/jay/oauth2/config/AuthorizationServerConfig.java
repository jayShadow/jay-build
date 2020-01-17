package cn.jay.oauth2.config;

import cn.jay.security.service.impl.UserPwdServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyPair;
import java.util.*;

/**
 * @Author: Jay
 * @Date: 2020/1/13 16:28
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final ClientDetailsService clientDetailsService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    public AuthorizationServerConfig(@Qualifier("clientService") ClientDetailsService clientDetailsService,
                                     @Qualifier("userPwdService") UserDetailsService userDetailsService,
                                     AuthenticationManager authenticationManager) {
        this.clientDetailsService = clientDetailsService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        log.info("=======init AuthorizationServerConfig finish==========");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()")    // /oauth/token_key 访问权限
                .checkTokenAccess("isAuthenticated()")  // /oauth/check_token 访问权限
                .allowFormAuthenticationForClients();   // 允许表单验证 client 授权
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("server.jks"), "123456".toCharArray())
                .getKeyPair("oauth2", "changeme".toCharArray());
        converter.setKeyPair(keyPair);
        return converter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore());
        endpoints.authenticationManager(authenticationManager);
        endpoints.accessTokenConverter(accessTokenConverter());
    }

}
