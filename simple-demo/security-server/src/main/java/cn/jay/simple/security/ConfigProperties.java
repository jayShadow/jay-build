package cn.jay.simple.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author: Jay
 * @Date: 2020/1/3 14:19
 */
@Data
@ConfigurationProperties(prefix = "config", ignoreUnknownFields = false)
public class ConfigProperties {

    /**
     * SWAGGER参数
     */
    private final Swagger swagger = new Swagger();
    /**
     * 安全认证
     */
    private final Auth auth = new Auth();

    /**
     * SWAGGER接口文档参数
     */
    @Data
    public static class Swagger {
        private String title;
        private String description;
        private String version;
        private String termsOfServiceUrl;
        private String contactName;
        private String contactUrl;
        private String contactEmail;
        private String license;
        private String licenseUrl;
    }

    @Data
    public static class Auth {
        private Integer tokenExpireTime;
        private Integer rememberMeExpireTime;
        private Integer loginTimeLimit;
        private Integer loginAfterTime;
        private List<String> ignoreUrls;
    }

}
