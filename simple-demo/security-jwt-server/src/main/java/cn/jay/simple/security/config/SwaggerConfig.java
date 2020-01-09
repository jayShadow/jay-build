package cn.jay.simple.security.config;

import cn.jay.simple.security.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @Author: Jay
 * @Date: 2020/1/3 16:00
 */
@Slf4j
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    /**
     * Swagger Springfox configuration.
     *
     * @param properties the properties of the application
     * @return the Swagger Springfox configuration
     */
    @Bean
    public Docket createRestApi(ConfigProperties properties) {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                properties.getSwagger().getContactName(),
                properties.getSwagger().getContactUrl(),
                properties.getSwagger().getContactEmail());

        ApiInfo apiInfo = new ApiInfo(
                properties.getSwagger().getTitle(),
                properties.getSwagger().getDescription(),
                properties.getSwagger().getVersion(),
                properties.getSwagger().getTermsOfServiceUrl(),
                contact,
                properties.getSwagger().getLicense(),
                properties.getSwagger().getLicenseUrl(),
                new ArrayList<>());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .select()
                .paths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();

        watch.stop();
        log.info("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

}
