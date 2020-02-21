package cn.jay.oauth2;

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
@MapperScan("cn.jay.security.mapper")
@ComponentScan({"cn.jay.security", "cn.jay.oauth2", "cn.jay.mybatis"})
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class);
    }

}
