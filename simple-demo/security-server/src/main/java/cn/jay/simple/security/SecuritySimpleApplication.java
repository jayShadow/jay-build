package cn.jay.simple.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author: Jay
 * @Date: 2019/12/31 11:44
 */
@SpringBootApplication
@EnableConfigurationProperties({ConfigProperties.class})
public class SecuritySimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuritySimpleApplication.class, args);
    }

}
