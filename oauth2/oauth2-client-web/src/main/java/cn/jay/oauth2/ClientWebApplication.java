package cn.jay.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

/**
 * @Author: Jay
 * @Date: 2020/1/19 14:55
 */
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class ClientWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientWebApplication.class);
    }

}
