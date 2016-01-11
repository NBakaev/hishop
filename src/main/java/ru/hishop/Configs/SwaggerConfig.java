package ru.hishop.Configs;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/11/2016.
 * All Rights Reserved
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .build();
    }

    private springfox.documentation.service.ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .build();
    }
}