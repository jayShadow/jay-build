//package cn.jay.oauth2.config;
//
//import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesRegistrationAdapter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
//import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
//import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
//import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//
///**
// * @Author: Jay
// * @Date: 2020/1/19 14:55
// */
//@Configuration
//@EnableOAuth2Client
//public class ClientConfig {
//
//    @Bean
//    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext context, OAuth2ProtectedResourceDetails details) {
//        OAuth2RestTemplate template = new OAuth2RestTemplate(details, context);
//
//        AuthorizationCodeAccessTokenProvider authCodeProvider = new AuthorizationCodeAccessTokenProvider();
//        authCodeProvider.setStateMandatory(false);
//        AccessTokenProviderChain provider = new AccessTokenProviderChain(
//                Arrays.asList(authCodeProvider));
//        template.setAccessTokenProvider(provider);
//    }
//
//    /**
//     * 注册处理redirect uri的filter
//     * @param oauth2RestTemplate
//     * @param tokenService
//     * @return
//     */
//    @Bean
//    public OAuth2ClientAuthenticationProcessingFilter oauth2ClientAuthenticationProcessingFilter(
//            OAuth2RestTemplate oauth2RestTemplate,
//            RemoteTokenServices tokenService) {
//        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(redirectUri);
//        filter.setRestTemplate(oauth2RestTemplate);
//        filter.setTokenServices(tokenService);
//
//
//        //设置回调成功的页面
//        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler() {
//            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                this.setDefaultTargetUrl("/home");
//                super.onAuthenticationSuccess(request, response, authentication);
//            }
//        });
//        return filter;
//    }
//
//    /**
//     * 注册check token服务
//     * @param details
//     * @return
//     */
//    @Bean
//    public RemoteTokenServices tokenService(OAuth2ProtectedResourceDetails details) {
//        RemoteTokenServices tokenService = new RemoteTokenServices();
//        tokenService.setCheckTokenEndpointUrl(checkTokenUrl);
//        tokenService.setClientId(details.getClientId());
//        tokenService.setClientSecret(details.getClientSecret());
//        return tokenService;
//    }
//
//}
