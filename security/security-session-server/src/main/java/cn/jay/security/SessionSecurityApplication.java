package cn.jay.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author: Jay
 * @Date: 2019/12/31 11:44
 */
@SpringBootApplication
@EnableConfigurationProperties({ConfigProperties.class})
@MapperScan("cn.jay.security.mapper")
public class SessionSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionSecurityApplication.class, args);
    }

}
