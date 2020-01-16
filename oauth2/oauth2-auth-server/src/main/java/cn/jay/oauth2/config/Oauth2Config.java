//package cn.jay.oauth2.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.token.TokenService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
///**
// * @Author: Jay
// * @Date: 2020/1/15 13:48
// */
//@Slf4j
//@Configuration
//public class Oauth2Config {
//
//    private final AuthenticationManager authenticationManager;
//
//    private final ClientDetailsService clientDetailsService;
//
//    public Oauth2Config(@Qualifier("clientService") ClientDetailsService clientDetailsService,
//                        AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//        this.clientDetailsService = clientDetailsService;
//        log.info("=======init Oauth2Config finish==========");
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("secret");
//        return converter;
//    }
//
//    @Bean
//    public AuthorizationServerTokenServices tokenServices() {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(tokenStore());
////        tokenServices.setAuthenticationManager(authenticationManager);
////        tokenServices.setSupportRefreshToken(true);
////        tokenServices.setClientDetailsService(clientDetailsService);
//        return tokenServices;
//    }
//
//}
