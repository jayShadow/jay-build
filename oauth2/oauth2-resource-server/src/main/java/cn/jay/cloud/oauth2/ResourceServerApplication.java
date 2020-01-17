package cn.jay.cloud.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

/**
 * @Author: Jay
 * @Date: 2020/1/13 16:50
 */
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class ResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class);
    }

}
