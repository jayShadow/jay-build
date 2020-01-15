package cn.jay.cloud.oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Jay
 * @Date: 2020/1/13 16:50
 */
@SpringBootApplication
@EnableConfigurationProperties({ConfigProperties.class})
@MapperScan("cn.jay.simple.security.mapper")
@ComponentScan({"cn.jay.simple.security","cn.jay.cloud.oauth2"})
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class);
    }

}
